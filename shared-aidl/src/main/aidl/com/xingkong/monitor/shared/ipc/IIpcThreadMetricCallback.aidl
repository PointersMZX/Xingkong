// IIpcThreadMetricCallback.aidl
package com.xingkong.monitor.shared.ipc;

/**
 * 线程负载回调（运行核心/迁移次数/Affinity/核心分布）。
 * 来自 Perfetto trace 解析。
 */
interface IIpcThreadMetricCallback {
    void onThreadMetric(long timestampNanos,
                        in String[] threadNames,
                        in int[] avgLoadPercent,
                        in int[] maxLoadPercent,
                        in int[] mainCore);
}
