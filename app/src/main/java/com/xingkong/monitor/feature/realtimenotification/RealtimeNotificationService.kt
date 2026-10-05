package com.xingkong.monitor.feature.realtimenotification

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.service.notification.NotificationListenerService
import android.view.accessibility.AccessibilityEvent
import androidx.lifecycle.LifecycleService

/**
 * 实时性能通知前台服务（specialUse FGS）。
 *
 * 用户主动启用后，将实时性能数据通过通知推送展示。
 * TODO 接力 6：订阅 DaemonClient realtime-metric 流，更新通知内容。
 */
class RealtimeNotificationService : LifecycleService()

/**
 * 无障碍服务：监听前台 App 切换。
 *
 * 用于 FPS 记录的自动开始/停止（目标 App 切换 → 停止并保存当前记录）。
 * TODO 接力 6：onAccessibilityEvent 处理 TYPE_WINDOW_STATE_CHANGED，识别前台包名。
 */
class RealtimeNotificationAccessibilityService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // TODO 接力 6
    }

    override fun onInterrupt() {
        // no-op
    }
}
