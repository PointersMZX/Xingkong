package com.xingkong.monitor.server.daemon

/**
 * Daemon Java 侧入口（被 app_process 拉起）。
 *
 * 启动命令（App 内显示给用户复制）：
 *   CLASSPATH=/data/app/.../base.apk app_process / com.xingkong.monitor.server.daemon.DaemonRuntime \
 *     --classpath <apk> --native-library-dir <lib dir>
 *
 * 与原 App libmetric_starter.so（C++）等价，但用 Kotlin 实现简化。
 *
 * UID 检测：
 *   - root(0)：直接初始化 daemon
 *   - shell/adb(2000)：直接初始化 daemon
 *   - 其他：报错（需 Root 启动或 Shell 命令启动）
 *
 * TODO 接力 2：实现 main()，加载 libxingkong_daemon，调 nativeInit()，
 *   启动 Binder 服务端，通过 BinderProvider 推回 IDaemonMain 给 App。
 */
object DaemonRuntime {

    @JvmStatic
    fun main(args: Array<String>) {
        // TODO 接力 2
        println("Xingkong Daemon Runtime v1.0.0")
        println("args: ${args.joinToString(" ")}")
    }
}
