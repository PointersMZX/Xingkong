package com.xingkong.helper

import com.xingkong.helper.sampler.GpuSamplerHolder

/**
 * IMetricHelper.Stub 实现：暴露 GPU 频率采样给 daemon。
 *
 * Helper APK 在 shell/system UID 下运行，能读 GPU 频率节点。
 */
class HelperServiceImpl : IMetricHelper.Stub() {

    override fun getGpuFrequencyMhz(): Int =
        GpuSamplerHolder.sampler.readFrequencyMhz()

    override fun getGpuUsagePercent(): Int {
        // TODO 接力 3：读 KGSL gpubusy / Mali utilization
        return -1
    }

    override fun getGpuFrequencySource(): String =
        GpuSamplerHolder.sampler.probeFrequencySource()

    override fun isGpuFrequencyAvailable(): Boolean =
        GpuSamplerHolder.sampler.isAvailable()

    override fun getGpuFrequencyTimestampNanos(): Long =
        GpuSamplerHolder.sampler.getTimestampNanos()
}
