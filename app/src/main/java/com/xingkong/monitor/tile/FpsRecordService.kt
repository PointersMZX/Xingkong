package com.xingkong.monitor.tile

import android.service.quicksettings.TileService
import androidx.lifecycle.LifecycleService

/**
 * FPS 记录前台服务（specialUse FGS）。
 *
 * 用户通过快捷磁贴或 App 内 FPS recorder 启动后，持续记录前台 App 的帧率/FrameTime/Jank
 * 及关联的 CPU/GPU/DDR/Power/温度/线程负载。
 *
 * 停止触发：手动 / 计时结束 / 前台 App 变化 / Daemon 断开 / 目标 App 退出 / 后台被杀。
 *
 * TODO 接力 5：接 DaemonClient 开始会话，落盘 Room 记录。
 */
class FpsRecordService : LifecycleService()

/**
 * 快捷设置磁贴：开始/停止 FPS 记录。
 * 持有 BIND_QUICK_SETTINGS_TILE 权限。
 *
 * TODO 接力 5：onClick → toggle FpsRecordService。
 */
class FpsRecordTileService : TileService() {
    override fun onClick() {
        super.onClick()
        // TODO 接力 5
    }
}
