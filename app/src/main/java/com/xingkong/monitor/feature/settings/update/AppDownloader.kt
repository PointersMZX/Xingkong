package com.xingkong.monitor.feature.settings.update

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * 应用自更新下载器。
 *
 * 下载源：GitHub Release（保留 ghproxy 镜像 fallback，与原 App 一致）。
 * 公益版：无 Firebase RemoteConfig 控制更新通道。
 */
class AppDownloader {

    /**
     * 下载完成 Receiver（Android DownloadManager 发广播）。
     */
    class DownloadCompleteReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // TODO 接力 4：拿到下载 ID，触发安装 Intent（REQUEST_INSTALL_PACKAGES）
        }
    }
}
