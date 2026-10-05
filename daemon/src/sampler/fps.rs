//! FpsSampler（骨架 — 接力 2/5 实现）
//!
//! 数据源：
//!   - SurfaceFlinger FrameTimeline（`dumpsys SurfaceFlinger --latency`）
//!   - ftrace：binder/binder_transaction, binder/binder_transaction_received
//!   - Perfetto trace：/data/misc/perfetto-traces/xingkong-fps-record.perfetto-trace
//!
//! 帧时间 + Jank 计算：
//!   - FrameTime：单帧渲染耗时（ns）
//!   - Jank：帧时间 > 16.6ms × 2（60Hz 标准下）或 > 刷新率倒数 × 2

pub struct FpsSampler;

impl FpsSampler {
    pub fn new() -> Self { Self }
    // TODO 接力 5：start_session / stop_session / on_frame
}
