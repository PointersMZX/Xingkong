// IIpcRealtimeFpsCallback.aidl
package com.xingkong.monitor.shared.ipc;

/**
 * 实时 FPS 回调（每帧或聚合窗口推送）。
 */
interface IIpcRealtimeFpsCallback {
    void onRealtimeFps(long timestampNanos, int fps, long frameTimeNanos, int jank);
}
