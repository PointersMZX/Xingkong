// IDaemonMain.aidl
package com.xingkong.monitor.shared.ipc;

import com.xingkong.monitor.shared.ipc.IIpcBasicMetricCallback;
import com.xingkong.monitor.shared.ipc.IIpcRealtimeFpsCallback;
import com.xingkong.monitor.shared.ipc.IIpcFpsRecordCallback;
import com.xingkong.monitor.shared.ipc.IIpcThreadMetricCallback;
import com.xingkong.monitor.shared.ipc.IIpcForegroundAppCallback;
import com.xingkong.monitor.shared.ipc.IIpcProcessMetricCallback;
import com.xingkong.monitor.shared.ipc.IIpcProcessRecordCallback;

/**
 * Daemon 主接口。
 *
 * Daemon 启动后通过 BinderProvider.call("attachBinder", extras=Binder) 推回 App，
 * App 转为 IDaemonMain 后调用注册/控制方法。
 *
 * 公益版：无 isMembershipActive 检查、无 GPU 会员墙（GPU 频率需 helper.apk，但不再卡会员）。
 */
interface IDaemonMain {
    // 注册/反注册回调
    void registerBasicMetric(in IIpcBasicMetricCallback cb);
    void unregisterBasicMetric(in IIpcBasicMetricCallback cb);
    void registerRealtimeFps(in IIpcRealtimeFpsCallback cb);
    void unregisterRealtimeFps(in IIpcRealtimeFpsCallback cb);
    void registerForegroundApp(in IIpcForegroundAppCallback cb);
    void unregisterForegroundApp(in IIpcForegroundAppCallback cb);
    void registerProcessMetric(in IIpcProcessMetricCallback cb);
    void unregisterProcessMetric(in IIpcProcessMetricCallback cb);

    // FPS 记录会话
    long startFpsRecord(String targetPackage, long durationMillis, in IIpcFpsRecordCallback cb);
    void stopFpsRecord();

    // 进程记录会话
    long startProcessRecord(String targetPackage, long durationMillis, in IIpcProcessRecordCallback cb);
    void stopProcessRecord();

    // Daemon 元信息
    int getDaemonUid();
    String getDaemonVersion();
    boolean isGpuFrequencyAvailable();
    boolean isHelperAttached();
}
