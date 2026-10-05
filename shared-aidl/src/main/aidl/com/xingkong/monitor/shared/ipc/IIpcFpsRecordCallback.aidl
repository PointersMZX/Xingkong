// IIpcFpsRecordCallback.aidl
package com.xingkong.monitor.shared.ipc;

/**
 * FPS 记录会话回调。
 * 会话停止 reason 取值：
 *   0 = 手动停止 / 1 = 计时结束 / 2 = 前台 App 变化
 *   3 = Daemon 断开 / 4 = 目标 App 退出 / 5 = 后台被杀 / 6 = 异常
 */
interface IIpcFpsRecordCallback {
    void onFpsRecordStarted(long sessionId, String targetPackage);
    void onFpsRecordStopped(int reason, String savePath);
}
