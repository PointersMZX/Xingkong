package com.xingkong.monitor.boot

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * 开机自启 Receiver。
 *
 * TODO 接力 6：开机后检查 Daemon 自动重连策略（可选：提示用户启动 daemon）。
 * 公益版：不静默拉起 daemon（避免惊扰用户），仅刷新激活态。
 */
class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // TODO 接力 6
    }
}
