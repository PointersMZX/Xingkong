package com.xingkong.monitor.core.telemetry

import android.content.Context
import android.util.Log
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * 本地崩溃日志记录器（替代 Firebase Crashlytics）。
 *
 * 公益版：完全离线，崩溃堆栈写 /data/data/com.xingkong.monitor/files/crashes/。
 * 用户可在设置页导出反馈。
 */
object LocalCrashLogger {
    private const val TAG = "Xingkong/Crash"
    private const val DIR = "crashes"

    fun log(context: Context, throwable: Throwable) {
        Log.e(TAG, "uncaught", throwable)
        runCatching {
            val dir = File(context.filesDir, DIR).apply { mkdirs() }
            val ts = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US).format(Date())
            val out = File(dir, "$ts.txt")
            val sw = StringWriter()
            throwable.printStackTrace(PrintWriter(sw))
            val content = buildString {
                appendLine("时间: ${Date()}")
                appendLine("设备: ${android.os.Build.MANUFACTURER} ${android.os.Build.MODEL}")
                appendLine("Android: ${android.os.Build.VERSION.RELEASE} (SDK ${android.os.Build.VERSION.SDK_INT})")
                appendLine("应用: 星控 v1.0.0")
                appendLine("--- stack ---")
                append(sw.toString())
            }
            out.writeText(content)
        }
    }
}
