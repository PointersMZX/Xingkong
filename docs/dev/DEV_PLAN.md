# 星控 v1.0.0 — 开发计划 DEV_PLAN（多轮接力基准）

> **方案 A：完整复刻 + 完全离线（删 Firebase 全部）**
> 工程量数周，多轮接力按本文件阶段推进

## 阶段进度总览

| 阶段 | 内容 | 状态 |
|---|---|---|
| 0 | 环境装机 | ✅ 完成（2026-10-05） |
| 1 | 项目骨架 | ✅ 完成（2026-10-05，assembleDebug BUILD SUCCESSFUL 34s，双 APK 产出） |
| 2-3 | daemon Rust crate 实现 + helper GpuSampler | ⬜ 接力 1 |
| 4 | App feature 模块 | ⬜ 接力 2-3 |
| 5 | FPS 记录 | ⬜ 接力 3-4 |
| 6 | 悬浮窗 + 实时通知 + 校准 + 多图标 | ⬜ 接力 4 |
| 7 | 构建验证（真机冒烟 + assembleRelease + CI 发布） | ⬜ 接力 5 |

### 阶段 0 实际执行记录（2026-10-05）

- JDK 21.0.12.1 → `D:\Agent\tools\jdk21`（Microsoft OpenJDK zip，aka.ms 下载）
- NDK r27c → `D:\Agent\tools\android-ndk-r27c`（781MB，dl.google.com 约 6 分钟）
- Rust stable 1.99.0 + aarch64-linux-android target → `D:\Agent\tools\{cargo,rustup}`
- platform-tools（adb）→ `D:\Agent\tools\platform-tools`（**坑：7z 解压多一层嵌套需手动拍平**）
- cmdline-tools → SDK 目录 `cmdline-tools\latest`
- **坑 1：services.gradle.org 被 GFW 卡超时重连 → distributionUrl 换腾讯云镜像 `https://mirrors.cloud.tencent.com/gradle/gradle-8.9-all.zip`**
- **坑 2：maven 仓库加阿里云镜像（settings.gradle.kts pluginManagement + dependencyResolutionManagement 都加）**
- **坑 3：rustup-init 装完后 default toolchain 未设/manifest 缺失 → `rustup toolchain install stable --force` 修复；cargo 1.99.0 可用，rustc.exe 报组件不适用（接力 2 处理，daemon 编译时再修）**

### 阶段 1 实际执行记录（2026-10-05）

- 根配置 + 三 Gradle 模块（app/helper/shared-aidl）+ daemon Rust crate + docs 离线文档
- **坑 4：XML 主题 parent 用 `Theme.Material3.DayNight.NoActionBar` 报 AAPT not found（Compose BOM 不带 Material3 XML 主题）→ 改 `android:Theme.Material.NoActionBar` 平台主题**
- **坑 5：drawable vector `android:tint="?attr/colorControlNormal"` 报 attr not found（同因）→ 直接 fillColor 白色**
- **坑 6：LocalCrashLogger 文件末尾写了同名私有扩展函数 `writeText` 调自己 → Kotlin 递归类型推断报错 5 个 → 删掉扩展函数、buildString 赋值给 val 再 writeText**
- 产物：app-debug.apk 58M + helper-debug.apk 1.3M，badging 全过
- 构建命令：`cd /d/Agent/Xingkong && JAVA_HOME=/d/Agent/tools/jdk21 ./gradlew assembleDebug --no-daemon`（`build.sh` 已存）

## 阶段 2：daemon Rust crate（接力 1）

- `Cargo.toml`：android-binder + tokio + rustls + socket2 + once_cell + jni
- AIDL 生成：7 个回调接口（`IIpcFpsRecordCallback` / `IIpcBasicMetricCallback` / `IIpcRealtimeFpsCallback` / `IIpcThreadMetricCallback` / `IIpcForegroundAppCallback` / `IIpcProcessMetricCallback` / `IIpcProcessRecordCallback`）+ `IDaemonMain`（主接口）
- `DaemonNativeBridge` JNI 桥（与 App 的 server.daemon 包对接）
- 采样器骨架：
  - `CpuSampler`（/proc/stat + cpufreq + ftrace）
  - `MemorySampler`（/proc/meminfo/swaps/vmstat）
  - `BatterySampler`（/sys/class/power_supply/battery/*）
  - `ThermalSampler`（/sys/class/thermal/* + 厂家节点）
  - `GpuSampler`（KGSL/MTK/Mali/Tegra/PowerVR 节点，部分转 helper）
  - `DdrSampler`（探测节点）
  - `FpsSampler`（SurfaceFlinger FrameTimeline + ftrace）
  - `ProcessSampler`（/proc/<pid>/stat + 冻结状态 FreezerV1/V2）
- Perfetto trace 集成（`/data/misc/perfetto-traces/xingkong-*.perfetto-trace`）
- 厂商特化：OPPO/Vivo LTPO、一加 AdfrFactoryMode

## 阶段 3：helper.apk（接力 1）

- `HelperMainActivity` + `HelperBinderProvider`
- `IMetricHelper` AIDL
- `GpuSampler` 全套 SoC 节点：
  - Mali：`/sys/devices/*.mali/clock`、`/sys/class/misc/mali0/device/clock`
  - 高通 KGSL：`/sys/class/kgsl/kgsl-3d0/{gpuclk,devfreq/cur_freq}`、`/sys/devices/*/kgsl/kgsl-3d0/gpuclk`
  - Devfreq：`/sys/class/devfreq/gpufreq/cur_freq`
  - MTK：`/proc/gpufreq/*`（v1/v2 opp 表）
  - Tegra：`/sys/kernel/tegra_gpu/gpu_rate`
  - PowerVR/OMAP：`/sys/devices/platform/omap/pvrsrvkm.0/sgxfreq/frequency`
  - GED hal：`/sys/kernel/ged/hal/current_freqency`
- ADB 安装命令：`pm install -r -t --bypass-low-target-sdk-block /data/local/tmp/xingkong-helper.apk`
- minSdk=21, target=26（故意低，绕限制）

## 阶段 4：App 主体 feature 模块（接力 2-3）

按依赖顺序：
1. `core/database`（Room）+ `core/data/recording`
2. `core/model/appmanager` + `feature/appmanager`
3. `feature/home`（首页：内存/GPU/CPU 聚合/CPU 进程列表）
4. `feature/memory`（Swap/Zram/MemoryInfo）
5. `feature/process`（进程列表 + 冻结状态）
6. `feature/performance`（性能详情）
7. `feature/onboarding`（介绍/启动服务/校准三页）
8. `feature/settings`（启动/更新/采样/日志/社区 + 离线文档）
9. `ipc`（BinderProvider 客户端）
10. `server/daemon`（DaemonNativeBridge + FakeContext 兜底）
11. `navigation`（Navigation3）

## 阶段 5：FPS 记录（接力 3-4）

- `feature/fpsrecord`（FPS recorder 悬浮窗 + 开始/停止/计时）
- `feature/processrecord`（ProcessRecordService 前台服务）
- `feature/threadallocation`（线程负载详情：运行核心/迁移次数/Affinity/核心分布）
- `tile/FpsRecordService` + `tile/FpsRecordTileService`（快捷磁贴）
- `core/telemetry`（游戏线程识别表 60+ 包名 + Draw Thread/Update Thread/GPU Submission 等）

## 阶段 6：悬浮窗 + 实时通知（接力 4）

- `overlay/manager`（OverlayManager：实时数据胶囊 + FPS recorder 胶囊）
- `overlay/notification`（RealtimeNotificationOverlayReceiver + RealtimeNotificationService）
- `feature/realtimenotification`（RealtimeNotificationAccessibilityService 监听前台 App）

## 阶段 7：校准 + 多图标（接力 4）

- 校准流程（自动探测电流倍率 + 串联双电芯 + 手动校准）
- 8 个 activity-alias（Default/Simple/Gold{Detailed,Simple}/Dark{Detailed,Simple}/DarkGold{Detailed,Simple}）

## 阶段 8：构建验证（接力 5）

- `./gradlew assembleDebug` 跑通
- debug APK 安装 + 冒烟（启动到首页）
- daemon + helper ADB 装机 + Binder 连通测试（需真机）
- `./gradlew assembleRelease` 出 release APK

## 接力规则

- 每轮接力前先读本文件 + PRD.md + ANALYSIS.md
- 每轮完成一个阶段，更新本文件状态
- 内存条目（[[xingkong-project]]）记录关键决策与坑
- 临时产物落 D:\Agent\_tmp\xingkong\，用完即删
- 用户可见文案（设置/关于/离线文档）一律用户视角，不写内话
