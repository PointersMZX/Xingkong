# 星控（Xingkong）v1.0.0 — 参考包逆向分析 Brief

> 参考包：`D:\Agent\example\2\Metric-v1.7.6.apk`（com.itos.metric.monitor，v1.7.6-release，versionCode 896）
> 性质：第三方废弃 App，**用户无源码、无版权** → 必须干净重写，不可复用其 native 二进制
> 目标：公益版，会员功能全开，免费

---

## 一、原 App 架构画像

### 三层特权架构（Shizuku 风格，必须保留）

| 层 | 组件 | 权限身份 | 作用 |
|---|---|---|---|
| **App 主体** | `com.itos.metric.monitor`（Kotlin + Compose + Hilt + Room + DataStore + Navigation3 + Material3） | 普通 UID | UI、悬浮窗、记录管理、磁贴、前台服务 |
| **Metric Daemon** | `libmetric_daemon.so`（**Rust**，含 `android-binder`/`rustls`/`socket2`/`tokio` crate）+ `libmetric_gpu.so`（C++）+ `libmetric_starter.so`（C++） | root(0) 或 shell/adb(2000) | CPU/GPU/内存/DDR/温度/电池/FPS 采样 + Perfetto trace + ftrace |
| **Helper APK** | `assets/helper/metric-helper.apk`（`com.itos.metric.helper` v1.1，minSdk=21/target=26 故意低） | shell/system（ADB 装到 /data/local/tmp） | GPU 频率节点采样（KGSL/MTK/Mali/Tegra/PowerVR 全套） |

### IPC 通道（Binder 推回 App）
- 主 App 暴露 `BinderProvider`（ContentProvider，authorities=`com.itos.metric.monitor.binderProvider`，permission=`INTERACT_ACROSS_USERS_FULL`，exported=true）
- helper 暴露 `HelperBinderProvider`（authorities=`com.itos.metric.helper.binderProvider`）
- daemon 启动后通过 BinderProvider 把 Binder 推回 App，App 拿到后开始拉数据
- AIDL 接口（7 个回调）：`IIpcFpsRecordCallback` / `IIpcBasicMetricCallback` / `IIpcRealtimeFpsCallback` / `IIpcThreadMetricCallback` / `IIpcForegroundAppCallback` / `IIpcProcessMetricCallback` / `IIpcProcessRecordCallback`

### 启动方式（用户文档原文）
- **Root 启动**：root 设备直接 su 执行 daemon
- **Shell 命令启动**：ADB Shell 或本地终端粘贴 App 显示的命令（命令含当前安装包 native lib 路径）
- **Helper 安装命令**（native 字符串里挖到）：`pm install -r -t --bypass-low-target-sdk-block /data/local/tmp/metric-helper.apk`
- starter 用 `app_process --classpath --native-library-dir` 拉 daemon，UID 检测 root(0)/shell(2000)

### 主 APK 组件清单
- `MainActivity`（含 `metric://payment/return` 支付回调 deeplink）
- 8 个 activity-alias（多图标变体：Default/Simple/Gold{Detailed,Simple}/Dark{Detailed,Simple}/DarkGold{Detailed,Simple}）
- 4 个前台服务（foregroundServiceType=0x40000000=specialUse）：
  - `FpsRecordService`（FPS 记录）
  - `FpsRecordTileService`（快捷磁贴，BIND_QUICK_SETTINGS_TILE）
  - `ProcessRecordService`（本地进程性能持续记录）
  - `RealtimeNotificationService`（App 侧实时性能通知）
- `RealtimeNotificationAccessibilityService`（监听前台 App 切换，触发 FPS 记录开始/停止）
- `BootCompletedReceiver`（开机自启）
- `AppDownloader$DownloadCompleteReceiver`（应用自更新下载，走 `https://ghproxy.vip/` GitHub 代理）
- `BinderProvider` + `FileProvider`
- `ComponentShortcutActivity`（应用组件快捷方式）

### 功能模块（feature 包）
| 模块 | 功能 |
|---|---|
| home | 首页（内存/GPU/CPU 聚合卡片/CPU 进程列表） |
| performance | 性能详情 |
| process | 进程列表 + 冻结状态（FreezerV1/V2） |
| processrecord | 本地进程性能持续记录 |
| fpsrecord | FPS 记录（开始/停止/计时/会话保存） |
| threadallocation | 线程负载详情（运行核心/迁移次数/Affinity/核心分布） |
| memory | 内存详情（Swap/Zram/MemoryInfo） |
| realtimenotification | 实时性能通知（推送通知展示数据） |
| appmanager | 应用组件快捷方式（pinned shortcut） |
| onboarding | 首次引导（介绍/启动服务/校准三页） |
| settings | 设置（含 community + update 自更新） |
| membership | 会员（要删） |

### daemon 采样数据源（Rust native 字符串挖出）
- **CPU**：`/proc/stat`、`/sys/devices/system/cpu/cpu*/cpufreq/*`、ftrace `power/cpu_frequency`、`power/cpu_idle`
- **内存**：`/proc/meminfo`、`/proc/swaps`、`/proc/vmstat`、`/proc/sys/vm/swappiness`、`/proc/sys/vm/watermark_scale_factor`、`/sys/block/*`（swapfile）
- **GPU**：KGSL（高通 `/sys/class/kgsl/kgsl-3d0/gpuclk`、`devfreq/cur_freq`）、MTK v1/v2（`/proc/gpufreq/*`）、Mali（`/sys/devices/*.mali/clock`）、Tegra（`/sys/kernel/tegra_gpu/gpu_rate`）、PowerVR/OMAP
- **温度**：`/sys/class/thermal/thermal_zone*/temp` + MTK/高通/OMAP/Tegra/Samsung/HTC 各家节点（10+ 路径）
- **电池**：`/sys/class/power_supply/battery/{temp,voltage_now,current_now,capacity,status}`（`BatterySampler`）
- **DDR**：探测节点（`probe_nodes: DDR`）
- **FPS**：SurfaceFlinger `android.surfaceflinger.frametimeline` + ftrace
- **Perfetto trace**：`/data/misc/perfetto-traces/metric-fps-record.perfetto-trace` + `metric-process-record.perfetto-trace`
- **游戏线程识别表**（内置 60+ 包名 + 渲染线程名匹配）：Draw Thread / Update Thread / GPU Submission / MINECRAFT MAIN / SCERender / SDLThread / DAVA Engine / MiniRenderThrea 等
- **厂商特化**：OPPO/Vivo LTPO 自适应刷新率、一加 AdfrFactoryMode

### SDK 与依赖
- Kotlin + Compose + Hilt + Room + DataStore + Navigation3 + Material3 adaptive
- Firebase：Analytics + Crashlytics + RemoteConfig + Sessions
- Google play-services-ads-identifier + AD_ID + ACCESS_ADSERVICES（广告归因）
- androidx.work + androidx.room + Baseline Profile（启动优化）

---

## 二、会员体系定位（要全删）

### 会员墙功能（确认只有 1 项）
- **GPU 频率/占用** = 会员专属（`GpuMembershipRequired` / `showGpuMembershipRequiredDialog` / `navigateToHomeMembership` / `onMembershipRequired` / `membershipRequiredText`）
- 原因：helper.apk 要 ADB/Root 装到特权位置，原 App 把这条特权路径做成了付费功能
- **其余功能（CPU/内存/FPS/DDR/Power/温度/进程/线程负载/校准）不卡会员**

### 会员/支付类（全删）
- 类：`MembershipRoute` / `MembershipViewModel` / `MembershipRepository` / `MembershipPaymentViewModel` / `MembershipPreferencesDataSource` / `MembershipState` / `MembershipScreenUiState`
- 状态字段：`isMembershipActive` / `isPremium` / `is_premium_active` / `ispro` / `membershipActivatedAtEpochMillis`
- 设备绑定：`syncMembershipStateIfSerialChanged`（用 `ro.serialno` 设备序列号绑定会员）
- 支付方式：**Alipay（支付宝）+ Wxpay（微信支付）**
- 订单流程：`CreateOrder` / `CreatedMembershipOrder` / `AwaitingPayment` / `PaymentConfirmed` / `PaymentPending` / `PaymentTimedOut` / `OpenPaymentUrl` / `MissingPaymentUrl` / `restoreMembership` / `deactivateMembership` / `prepareMembership`
- deeplink：`metric://payment/return`（MainActivity intent-filter）
- Firebase RemoteConfig 键：`audience_membership` / `is_premium` / `vip_price` / `membership_entry` / `membership_required` / `metric_membership` / `payment_order_number` / `payment_type` / `in_app_purchase` / `ecommerce_purchase` / `add_payment_info` / `purchase_refund` / `last_membership_status_sn`
- Firebase 用户属性：`syncPremiumUserProperty`

### 公益化清单
1. 删 membership 整个 feature 包
2. 删 `metric://payment/return` deeplink
3. 删 Firebase RemoteConfig 会员键 + Analytics 电商事件
4. 删 AD_ID / ACCESS_ADSERVICES 广告归因权限
5. **GPU 功能默认全开**（不再卡会员）
6. **保留** daemon/helper.apk/Root/Shell 三件套（用户明确"无必要不违背原设计"——这是技术架构，不是会员功能）
7. Crashlytics 可保留（崩溃报告，无会员语义）

---

## 三、技术方案（待用户拍板）

### 关键约束
- 用户无原项目版权 → **不可复用 native 二进制**，必须干净重写
- 用户警告"涉及 ADB/Root 高权限，无必要不违背原设计" → **保留 daemon + helper 特权架构**
- 单 agent 单会话工程量上限 → 完整 Rust daemon 重写不现实（数百小时）

### 方案对比

| 维度 | A. 完整复刻 | B. 核心子集 | C. 核心子集 + 特权 helper（推荐） |
|---|---|---|---|
| daemon 语言 | Rust（重写） | Kotlin（内嵌） | Kotlin（内嵌） |
| App 主体 | Compose 全功能 | Compose 子集 | Compose 子集 |
| CPU/内存/温度/电池 | ✅ /proc + sysfs | ✅ /proc + sysfs | ✅ /proc + sysfs |
| FPS | ✅ SurfaceFlinger FrameTimeline + Perfetto | ✅ FrameMetrics + dumpsys | ✅ FrameMetrics + dumpsys |
| GPU 频率 | ✅ helper.apk（Kotlin 重写） | ❌ 不做 | ✅ Kotlin helper.apk（ADB 装） |
| DDR 频率 | ✅ daemon 探测 | ❌ 不做 | ❌ 留接口 |
| Perfetto trace | ✅ | ❌ | ❌ |
| 线程负载详情 | ✅ | ❌ | ❌ |
| 校准（电流倍率/双电芯） | ✅ | ❌ 简化（直接读 current_now） | ❌ 简化 |
| 游戏线程识别表 | ✅ 60+ 包名 | ❌ 用前台 App 通用识别 | ❌ 用前台 App 通用识别 |
| 悬浮窗 + 磁贴 + 快捷方式 | ✅ | ✅ | ✅ |
| 前台服务保活 | ✅ | ✅ | ✅ |
| 离线文档 | ✅ | ✅ | ✅ |
| 工程量 | 数周（不现实） | 3-5 天 | 5-7 天 |
| 公益版可用度 | 100% | 70% | 85% |

### 推荐：方案 C
- App 主体（Kotlin + Compose + Hilt + Room + DataStore + Material3）
- 内嵌 daemon（Kotlin，与 App 同进程或独立进程）：读 `/proc/stat` `/proc/meminfo` `/sys/class/thermal/*` `/sys/class/power_supply/battery/*`
- FPS：`Window.addFrameMetricsCallback`（API 24+）+ `dumpsys SurfaceFlinger --latency` 兜底
- 悬浮窗：SYSTEM_ALERT_WINDOW
- 磁贴：QuickSettingsTileService（BIND_QUICK_SETTINGS_TILE）
- 前台服务：FOREGROUND_SERVICE_SPECIAL_USE + 开机自启 + 电池白名单
- **特权 helper**（Kotlin，独立 APK `com.xingkong.helper`）：读 GPU 频率节点（KGSL/MTK/Mali），ADB 装 `pm install -r -t --bypass-low-target-sdk-block`，HelperBinderProvider 暴露给主 App
- 删会员/支付/Firebase Analytics/RemoteConfig/AD_ID；Crashlytics 可留
- 应用名「星控」、包名 `com.xingkong.monitor`、图标用 `D:\Agent\icon\Xingkong-icons`

---

## 四、待用户决策

1. **实现深度**：A / B / C（推荐 C）
2. **包名**：`com.xingkong.monitor`（建议）或用户指定
3. **Crashlytics 是否保留**：公益版可留（崩溃报告无商业语义）
