package com.xingkong.monitor

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * 星控 Application 入口。
 *
 * 公益版：无 Firebase 初始化、无广告归因、无会员状态同步。
 * 完全离线运行，崩溃日志写本地文件（[core.telemetry.LocalCrashLogger]）。
 */
@HiltAndroidApp
class XingkongApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        // TODO 接力 1：注入 LocalCrashLogger（写本地文件，替代 Firebase Crashlytics）
        // TODO 接力 1：注册 BinderProvider 客户端，等待 daemon Binder 推回
    }

    companion object {
        @Volatile
        lateinit var instance: XingkongApp
            private set
    }
}
