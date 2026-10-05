//! 采样器模块（接力 2 实现）
//!
//! 与原 App libmetric_daemon.so（Rust）同构：
//!   - CpuSampler：    /proc/stat + /sys/devices/system/cpu/cpu*/cpufreq/* + ftrace
//!   - MemorySampler： /proc/meminfo + /proc/swaps + /proc/vmstat
//!   - ThermalSampler：/sys/class/thermal/thermal_zone*/temp + 厂家节点
//!   - BatterySampler：/sys/class/power_supply/battery/{temp,voltage_now,current_now,capacity,status}
//!   - GpuSampler：    转发到 Helper binder（IMetricHelper）
//!   - DdrSampler：    探测节点
//!   - FpsSampler：    SurfaceFlinger FrameTimeline + ftrace + Perfetto trace
//!   - ProcessSampler：/proc/<pid>/stat + 冻结状态（FreezerV1/V2）

pub mod cpu;
pub mod memory;
pub mod thermal;
pub mod battery;
pub mod fps;
