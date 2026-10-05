package com.xingkong.monitor.server.daemon

/**
 * Daemon JNI 桥。
 *
 * 加载 libxingkong_daemon.so（Rust），声明 native 方法。
 * 与原 App com.itos.metric.monitor.server.daemon.DaemonNativeBridge 同构。
 *
 * 启动流程：
 *   1. App 主进程内 System.loadLibrary("xingkong_daemon")（仅 native 库加载）
 *   2. 真正 daemon 在独立进程（app_process 拉起，shell/system UID），
 *      由 DaemonRuntime 入口加载本库
 *   3. Rust 侧初始化 Binder 服务端，通过 BinderProvider.call("attachBinder") 推回 App
 */
class DaemonNativeBridge {

    companion object {
        init {
            // TODO 接力 2：仅 App 侧预加载（daemon 进程由 DaemonRuntime 加载）
            // runCatching { System.loadLibrary("xingkong_daemon") }
        }

        @JvmStatic external fun nativeInit()
        @JvmStatic external fun nativeGetDaemonUid(): Int
        @JvmStatic external fun nativeGetVersion(): String
    }
}
