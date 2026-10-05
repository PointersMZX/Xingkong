// IIpcBasicMetricCallback.aidl
package com.xingkong.monitor.shared.ipc;

/**
 * 基本指标回调（CPU/内存/Swap/温度/电池）。
 * Daemon 推送频率由设置控制（默认 1Hz）。
 */
interface IIpcBasicMetricCallback {
    void onBasicMetric(long timestampNanos,
                       int cpuUsagePercent,
                       long memTotalBytes,
                       long memAvailableBytes,
                       long swapTotalBytes,
                       long swapUsedBytes,
                       int thermalMilliC,
                       int batteryTempMilliC,
                       int batteryVoltageMilliV,
                       int batteryCurrentMicroA,
                       int batteryCapacity,
                       int batteryStatus);
}
