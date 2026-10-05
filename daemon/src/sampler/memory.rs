//! MemorySampler（骨架 — 接力 2 实现）
//!
//! 数据源：
//!   - /proc/meminfo（MemTotal/MemAvailable/SwapTotal/SwapFree）
//!   - /proc/swaps
//!   - /proc/vmstat
//!   - /sys/block/*/swapfile（zram）

pub struct MemorySampler;

impl MemorySampler {
    pub fn new() -> Self { Self }
    // TODO 接力 2：read_total_bytes / read_available_bytes / read_swap
}
