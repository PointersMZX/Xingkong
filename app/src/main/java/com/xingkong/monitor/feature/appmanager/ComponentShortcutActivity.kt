package com.xingkong.monitor.feature.appmanager

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 * 应用组件快捷方式入口 Activity。
 * TODO 接力 6：根据 intent 选择目标组件，请求 ShortcutManager.createShortcutResultIntent。
 */
class ComponentShortcutActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO 接力 6
        finish()
    }
}

/**
 * 快捷方式固定结果 Receiver。
 */
class ComponentShortcutPinnedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // TODO 接力 6
    }
}
