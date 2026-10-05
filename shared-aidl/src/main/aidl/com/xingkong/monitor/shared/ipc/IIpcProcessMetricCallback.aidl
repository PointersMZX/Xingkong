// IIpcProcessMetricCallback.aidl
package com.xingkong.monitor.shared.ipc;

/**
 * 进程指标回调（CPU 占用最高的进程列表）。
 */
interface IIpcProcessMetricCallback {
    void onProcessMetric(long timestampNanos,
                         in String[] packageNames,
                         in int[] pids,
                         in int[] cpuPercents);
}
