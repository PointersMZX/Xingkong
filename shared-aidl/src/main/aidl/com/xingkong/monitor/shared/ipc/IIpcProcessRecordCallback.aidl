// IIpcProcessRecordCallback.aidl
package com.xingkong.monitor.shared.ipc;

/**
 * 进程记录会话回调。
 * reason 取值同 IIpcFpsRecordCallback。
 */
interface IIpcProcessRecordCallback {
    void onProcessRecordStarted(long sessionId, String targetPackage);
    void onProcessRecordStopped(int reason, String savePath);
}
