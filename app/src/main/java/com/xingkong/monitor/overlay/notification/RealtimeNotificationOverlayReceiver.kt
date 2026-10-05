package com.xingkong.monitor.overlay.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * 实时通知 Overlay Receiver（daemon 持 INTERACT_ACROSS_USERS_FULL 权限调用）。
 * TODO 接力 6：daemon 推送实时数据，转发给 RealtimeNotificationService 渲染通知。
 */
class RealtimeNotificationOverlayReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // TODO 接力 6
    }
}
