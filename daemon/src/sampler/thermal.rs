//! ThermalSampler（骨架 — 接力 2 实现）
//!
//! 数据源（与原 App libmetric_daemon.so 同款全套节点）：
//!   - /sys/class/thermal/thermal_zone*/temp
//!   - /sys/devices/virtual/thermal/thermal_zone*/temp
//!   - MTK：     /proc/mtktz/mtktscpu
//!   - 高通：    /sys/class/hwmon/hwmon0/device/temp1_input
//!              /sys/devices/system/cpu/cpu0/cpufreq/cpu_temp
//!   - OMAP：    /sys/devices/platform/omap/omap_temp_sensor.0/temperature
//!   - Tegra：   /sys/devices/platform/tegra_tmon/temp1_input
//!              /sys/kernel/debug/tegra_thermal/temp_tj
//!   - Samsung： /sys/devices/platform/s5p-tmu/{temperature,curr_temp}
//!   - HTC：     /sys/htc/cpu_temp
//!   - 通用：    /proc/cpu_temp

pub struct ThermalSampler;

impl ThermalSampler {
    pub fn new() -> Self { Self }
    // TODO 接力 2：遍历 thermal_zone，返回 CPU/GPU/Battery 三类温度
}
