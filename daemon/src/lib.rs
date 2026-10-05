//! Xingkong Daemon — Rust native 库
//!
//! 由 Java 侧 `com.xingkong.monitor.server.daemon.DaemonRuntime` 通过
//! `System.loadLibrary("xingkong_daemon")` 加载，提供 JNI 函数供
//! `DaemonNativeBridge` 调用，实现：
//!   - CPU/内存/温度/电池/DDR 采样（/proc + /sys 节点 + ftrace）
//!   - FPS 采样（SurfaceFlinger FrameTimeline + Perfetto trace）
//!   - Binder 服务端：通过 `BinderProvider` 把 `IDaemonMain` 推回 App
//!   - Helper binder 代理：通过 `HelperBinderProvider` 拿 `IMetricHelper` 读 GPU 频率
//!
//! 启动方式（与原 App 一致，保留特权架构）：
//!   - Root 启动：root 设备 su 执行
//!   - Shell 命令启动：ADB Shell 粘贴 App 显示的命令（含 native lib 路径）
//!   - UID 检测：root(0) / shell+adb(2000)
//!
//! 公益版：无会员检查、无 Firebase 上报、完全离线。

#![allow(clippy::missing_docs)]

pub mod sampler;

use jni::JNIEnv;
use jni::objects::JClass;

/// JNI 入口：DaemonNativeBridge.nativeInit()
#[no_mangle]
pub extern "system" fn Java_com_xingkong_monitor_server_daemon_DaemonNativeBridge_nativeInit(
    _env: JNIEnv,
    _class: JClass,
) {
    // TODO 接力 2：初始化 android_logger，启动 tokio runtime，
    //   注册 BinderProvider 客户端，等待 Java 侧 triggerAttachBinder
}

/// JNI 入口：DaemonNativeBridge.nativeGetDaemonUid() -> Int
#[no_mangle]
pub extern "system" fn Java_com_xingkong_monitor_server_daemon_DaemonNativeBridge_nativeGetDaemonUid(
    _env: JNIEnv,
    _class: JClass,
) -> i32 {
    // TODO 接力 2：返回当前进程 UID（getuid()）
    0
}

/// JNI 入口：DaemonNativeBridge.nativeGetVersion() -> String
#[no_mangle]
pub extern "system" fn Java_com_xingkong_monitor_server_daemon_DaemonNativeBridge_nativeGetVersion(
    env: JNIEnv,
    _class: JClass,
) -> jni::sys::jstring {
    let v = std::ffi::CString::new("Xingkong Daemon v1.0.0").unwrap();
    env.new_string(v.to_str().unwrap())
        .map(|s| s.into_raw())
        .unwrap_or(std::ptr::null_mut())
}
