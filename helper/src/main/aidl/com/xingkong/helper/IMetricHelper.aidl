// IMetricHelper.aidl
package com.xingkong.helper;

/**
 * Helper 暴露给 Daemon 的 GPU 采样接口。
 *
 * Helper APK 在 shell/system UID 下运行（ADB 装特权位置），能读 GPU 频率节点。
 * Daemon 通过 HelperBinderProvider 拿到此 binder，调用 GPU 采样。
 *
 * 公益版：无会员检查（原 App GPU 频率卡会员，本版全开）。
 */
interface IMetricHelper {
    int getGpuFrequencyMhz();
    int getGpuUsagePercent();
    String getGpuFrequencySource();
    boolean isGpuFrequencyAvailable();
    long getGpuFrequencyTimestampNanos();
}
