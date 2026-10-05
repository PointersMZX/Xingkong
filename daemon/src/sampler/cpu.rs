//! CpuSampler（骨架 — 接力 2 实现）
//!
//! 数据源：
//!   - /proc/stat（CPU 占用）
//!   - /sys/devices/system/cpu/cpu*/cpufreq/*（频率）
//!   - ftrace：power/cpu_frequency, power/cpu_idle
//!   - /proc/<pid>/stat（进程级 CPU）

pub struct CpuSampler;

impl CpuSampler {
    pub fn new() -> Self { Self }

    /// 读 /proc/stat 第一行（aggregate CPU 占用）
    pub fn read_usage_percent(&self) -> i32 {
        // TODO 接力 2：解析 /proc/stat 第一行 user/nice/system/idle/... 计算 100 - idle*100/total
        -1
    }

    /// 读各核心当前频率（kHz）
    pub fn read_frequencies_khz(&self) -> Vec<u64> {
        // TODO 接力 2：遍历 /sys/devices/system/cpu/cpuN/cpufreq/scaling_cur_freq
        Vec::new()
    }
}
