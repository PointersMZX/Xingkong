// IIpcForegroundAppCallback.aidl
package com.xingkong.monitor.shared.ipc;

/**
 * 前台 App 变化回调（用于 FPS 记录自动停止）。
 */
interface IIpcForegroundAppCallback {
    void onForegroundAppChanged(String packageName, int uid);
}
