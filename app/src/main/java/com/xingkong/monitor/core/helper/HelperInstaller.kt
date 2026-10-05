package com.xingkong.monitor.core.helper

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File

/**
 * Helper 安装器（维持原设计：helper 嵌主 App assets，Release 只发 1 个主 APK）。
 *
 * 原设计：helper APK 嵌在主 App 的 assets/helper/xingkong-helper.apk（CI 构建时注入）。
 * 用户装了主 App 后，要 GPU 频率指标时：
 *   1. 主 App 把 assets 里的 helper APK 导出到公共 Download 目录
 *   2. 生成 ADB 命令，用户复制到 PC 端 ADB Shell 执行（push 到 /data/local/tmp + 特权安装）
 *   3. helper 以 shell/system UID 运行，主 App 通过 HelperBinderProvider 拿 GPU 数据
 *
 * 不装 helper 时：主 App 其余功能（CPU/内存/FPS/温度/电池）全正常，只 GPU 频率格显示 --。
 */
object HelperInstaller {

    const val HELPER_ASSET = "helper/xingkong-helper.apk"
    const val HELPER_PKG = "com.xingkong.helper"
    private const val TAG = "Xingkong/Helper"

    /** 把 assets 里的 helper APK 导出到公共 Download 目录，返回导出后的文件 */
    fun exportHelperApk(context: Context): File? {
        return runCatching {
            val dest = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                "xingkong-helper.apk"
            ).apply { parentFile?.mkdirs() }
            context.assets.open(HELPER_ASSET).use { input ->
                dest.outputStream().use { input.copyTo(it) }
            }
            Log.i(TAG, "helper APK 导出到 ${dest.absolutePath}")
            dest
        }.onFailure {
            Log.e(TAG, "导出 helper APK 失败（CI 未注入？）", it)
        }.getOrNull()
    }

    /**
     * 生成用户要复制到 PC 端 ADB Shell 执行的安装命令。
     * 与原 App "Shell 命令启动" 同款体验：命令里含特权安装 flag。
     */
    fun buildAdbInstallCommand(context: Context): String {
        val exported = exportHelperApk(context)
        val devicePath = exported?.absolutePath ?: "/sdcard/Download/xingkong-helper.apk"
        return """
            # 第一步：把主 App 导出的 helper APK 推到设备特权目录
            adb push $devicePath /data/local/tmp/xingkong-helper.apk
            # 第二步：以特权身份安装 helper（绕过低 targetSdk 检查）
            adb shell pm install -r -t --bypass-low-target-sdk-block /data/local/tmp/xingkong-helper.apk
            # 第三步：回到星控，Daemon 会自动连上 helper 的 GPU 采样
        """.trimIndent()
    }

    fun isEmbedded(context: Context): Boolean {
        return runCatching { context.assets.open(HELPER_ASSET).close(); true }
            .onFailure { Log.w(TAG, "helper 未嵌入 assets（本地调试正常，CI 产物含 helper）", it) }
            .getOrDefault(false)
    }
}
