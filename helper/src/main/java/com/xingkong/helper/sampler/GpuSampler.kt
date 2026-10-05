package com.xingkong.helper.sampler

import android.util.Log
import java.io.File

/**
 * GPU 频率采样器。
 *
 * 全套 SoC 节点适配（与原 App com.itos.metric.helper.sampler.GpuSampler 同构）：
 *   - 高通 KGSL：    /sys/class/kgsl/kgsl-3d0/{gpuclk, devfreq/cur_freq}
 *   - Devfreq：      /sys/class/devfreq/gpufreq/cur_freq
 *   - MTK v1/v2：    /proc/gpufreq/{gpufreq_opp_freq, gpufreq_fixed_freq_volt}
 *   - MTK v2 opp：   /proc/gpufreqv2/{fix_custom_freq_volt, fix_target_opp_index, gpu_working_opp_table, stack_signed_opp_table}
 *   - Mali：         /sys/devices/{11800000.mali, 14ac0000.mali}/clock
 *                  /sys/class/misc/mali0/device/clock
 *   - Tegra：        /sys/kernel/tegra_gpu/gpu_rate
 *   - PowerVR/OMAP： /sys/devices/platform/omap/pvrsrvkm.0/sgxfreq/frequency
 *   - GED hal：      /sys/kernel/ged/hal/current_freqency（typo，与原节点一致）
 *
 * 公益版：默认全开（原 App GPU 频率卡会员 GpuMembershipRequired）。
 */
class GpuSampler {

    /** 已知 GPU 频率节点路径（按 SoC 适配顺序） */
    private val frequencyPaths = listOf(
        // 高通 KGSL
        "/sys/class/kgsl/kgsl-3d0/gpuclk",
        "/sys/class/kgsl/kgsl-3d0/devfreq/cur_freq",
        "/sys/devices/fdb00000.qcom,kgsl-3d0/kgsl/kgsl-3d0/gpuclk",
        "/sys/devices/fdc00000.qcom,kgsl-3d0/kgsl/kgsl-3d0/gpuclk",
        "/sys/devices/soc.0/1c00000.qcom,kgsl-3d0/kgsl/kgsl-3d0/gpuclk",
        "/sys/devices/soc.0/fdb00000.qcom,kgsl-3d0/kgsl/kgsl-3d0/gpuclk",
        "/sys/devices/platform/kgsl-3d0.0/kgsl/kgsl-3d0/gpuclk",
        "/sys/devices/platform/kgsl-2d0.0/kgsl/kgsl-2d0/gpuclk",
        // Devfreq
        "/sys/class/devfreq/gpufreq/cur_freq",
        // MTK v1
        "/proc/gpufreq/gpufreq_opp_freq",
        "/proc/gpufreq/gpufreq_fixed_freq_volt",
        // MTK v2
        "/proc/gpufreqv2/fix_custom_freq_volt",
        "/proc/gpufreqv2/fix_target_opp_index",
        "/proc/gpufreqv2/gpu_working_opp_table",
        "/proc/gpufreqv2/stack_signed_opp_table",
        "/proc/gpufreqv2/stack_working_opp_table",
        // Mali
        "/sys/devices/11800000.mali/clock",
        "/sys/devices/14ac0000.mali/clock",
        "/sys/class/misc/mali0/device/clock",
        // Tegra
        "/sys/kernel/tegra_gpu/gpu_rate",
        // PowerVR/OMAP
        "/sys/devices/platform/omap/pvrsrvkm.0/sgxfreq/frequency",
        // GED hal（typo 与原节点一致）
        "/sys/kernel/ged/hal/current_freqency",
        "/sys/kernel/debug/ged/hal/current_freqency",
    )

    private var cachedSource: String? = null
    @Volatile private var lastTimestampNanos: Long = 0L

    /** 探测可用的 GPU 频率节点路径 */
    fun probeFrequencySource(): String {
        cachedSource?.let { return it }
        for (path in frequencyPaths) {
            val f = File(path)
            if (f.canRead()) {
                Log.i(TAG, "GPU 频率节点：$path")
                cachedSource = path
                return path
            }
        }
        cachedSource = ""
        return ""
    }

    fun isAvailable(): Boolean = probeFrequencySource().isNotEmpty()

    /** 读 GPU 频率（MHz） */
    fun readFrequencyMhz(): Int {
        val source = probeFrequencySource()
        if (source.isEmpty()) return -1
        return runCatching {
            val raw = File(source).readText().trim()
            // 节点值可能是 Hz / kHz / MHz，做适配
            val n = raw.toLongOrNull() ?: return@runCatching -1
            lastTimestampNanos = System.nanoTime()
            when {
                n > 1_000_000L -> (n / 1_000_000L).toInt()      // Hz
                n in 100L..10_000L -> n.toInt()                 // MHz
                else -> n.toInt()
            }
        }.getOrDefault(-1)
    }

    fun getTimestampNanos(): Long = lastTimestampNanos

    companion object {
        private const val TAG = "Xingkong/GpuSampler"
    }
}

/** GpuSampler 单例（与 Helper binder 共享） */
object GpuSamplerHolder {
    val sampler = GpuSampler()
}
