//! BatterySampler（骨架 — 接力 2 实现）
//!
//! 数据源：
//!   - /sys/class/power_supply/battery/temp（0.1°C）
//!   - /sys/class/power_supply/battery/voltage_now（μV）
//!   - /sys/class/power_supply/battery/current_now（μA）
//!   - /sys/class/power_supply/battery/capacity（%）
//!   - /sys/class/power_supply/battery/status（Charging/Discharging/...）
//!
//! Power（功率）= |current_now × voltage_now|，单位 mW
//! 串联双电芯校准见 onboarding 校准页（接力 4）。

pub struct BatterySampler;

impl BatterySampler {
    pub fn new() -> Self { Self }
    // TODO 接力 2：read_temp / read_voltage / read_current / read_capacity / read_status
}
