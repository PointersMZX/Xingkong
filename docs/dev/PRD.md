# 星控（Xingkong）v1.0.0 — 产品需求 PRD

## 一、产品定位

- **名称**：星控（Xingkong）
- **包名**：`com.xingkong.monitor`（helper：`com.xingkong.helper`）
- **版本**：v1.0.0
- **性质**：Android 专业级性能监视工具，App + daemon 双组件运行
- **定位**：公益版，会员功能全部开放，免费，不收取任何费用
- **来源**：参考废弃项目 Metric v1.7.6（com.itos.metric.monitor）的形态与功能，干净重写（用户无源码、无版权，不复用其 native 二进制）

## 二、用户原始诉求

1. 查看设备性能状态
2. 管理悬浮窗监视器
3. 记录帧率（FPS）相关会话数据
4. 涉及 Android 系统高权限操作（ADB 或 Root），如无必要不要违背原设计——保留 daemon + helper 特权架构
5. 会员功能全部开放，做公益，不再收取费用
6. 应用名「星控」，v1.0.0

## 三、功能清单（完整复刻 Metric v1.7.6）

### 3.1 性能监视（数据源：daemon + helper）
| 指标 | 数据源 | 是否需特权 |
|---|---|---|
| CPU 占用 + 频率 + Cycles | `/proc/stat`、`/sys/devices/system/cpu/cpu*/cpufreq/*`、ftrace | 否（普通 /proc 可读） |
| CPU 占用最高的进程列表 | `/proc/<pid>/stat` | 否 |
| 内存（RAM/Swap/Zram/MemoryInfo） | `/proc/meminfo`、`/proc/swaps`、`/proc/vmstat` | 否 |
| GPU 占用 + 频率 | KGSL/MTK/Mali/Tegra/PowerVR 节点 | **是（helper.apk 特权读）** |
| DDR 频率 | 探测节点 | 是（daemon 特权） |
| 温度（CPU/GPU/电池） | `/sys/class/thermal/thermal_zone*/temp` + 厂家节点 | 否（普通 sysfs 可读） |
| 电池（电流/电压/电量/状态） | `/sys/class/power_supply/battery/*` | 否 |
| Power（功率） | 电流 × 电压（含校准） | 否（依赖电流校准） |
| 帧率（FPS）/ Frame Time / Jank | SurfaceFlinger FrameTimeline + Perfetto | 否（dumpsys/FrameMetrics） |
| 线程负载（运行核心/迁移次数/Affinity/核心分布） | Perfetto trace 解析 | 是（daemon 写 trace） |

### 3.2 悬浮窗（OverlayManager）
- 实时数据胶囊（CPU/GPU/内存/温度/电池等可选字段）
- FPS recorder 胶囊（开始/停止/计时结束）
- 点击胶囊停止记录（可选设置）
- 字段不可用时显示 `--`（不补假数据）

### 3.3 FPS 记录
- 开始/停止/计时结束
- 自动停止触发：前台 App 变化、Daemon 断开、目标 App 退出、App 后台被杀
- 保存内容：FPS / FrameTime / Jank / CPU 占用+频率+Cycles / GPU 占用+频率 / DDR 频率 / Power / 电量 / 温度 / 线程负载
- 历史列表（设备概览 + 记录列表 + 删除）
- 详情页（图表 + 统计：max/min/avg/1% Low/5% Low + 线程负载详情）
- 长截图 + 导出记录

### 3.4 进程记录
- 本地进程性能持续记录（ProcessRecordService 前台服务）
- 进程冻结状态（FreezerV1/V2）

### 3.5 实时通知
- App 侧实时性能通知（推送通知展示数据）
- 无障碍服务监听前台 App 切换（触发 FPS 记录开始/停止）

### 3.6 应用管理
- 应用组件快捷方式（pinned shortcut）

### 3.7 首次引导
- 介绍页
- 启动服务页（Root 启动 / Shell 命令启动）
- 校准页（自动探测电流倍率 + 串联双电芯判断 + 手动校准）

### 3.8 快捷磁贴
- QuickSettingsTileService（BIND_QUICK_SETTINGS_TILE）
- 点击磁贴开始/停止 FPS 记录

### 3.9 设置
- 启动 / 更新 / 采样 / 日志
- 社区（community）
- 离线文档（docsify）

### 3.10 多图标变体
- 8 个 activity-alias（Default / Simple / Gold{Detailed,Simple} / Dark{Detailed,Simple} / DarkGold{Detailed,Simple}）

### 3.11 游戏线程识别表
- 60+ 包名 + 渲染线程名匹配（Draw Thread / Update Thread / GPU Submission / MINECRAFT MAIN / SCERender / SDLThread / DAVA Engine / MiniRenderThrea 等）

### 3.12 厂商特化
- OPPO/Vivo LTPO 自适应刷新率
- 一加 AdfrFactoryMode

## 四、公益化清单（相对原 App）

| 删除项 | 原因 |
|---|---|
| membership 整个 feature 包 | 公益版无会员 |
| `metric://payment/return` deeplink | 无支付 |
| Alipay/Wxpay 支付链 | 无支付 |
| Firebase Analytics | 无统计 |
| Firebase RemoteConfig（会员键） | 无云端控会员 |
| Firebase Sessions | 无会话统计 |
| Firebase Crashlytics | 完全离线（用户选定） |
| AD_ID / ACCESS_ADSERVICES 权限 | 无广告 |
| play-services-ads-identifier | 无广告 |

**保留**：daemon + helper 特权架构（用户明确"无必要不违背原设计"）

## 五、技术栈

- **App 主体**：Kotlin + Jetpack Compose + Hilt + Room + DataStore + Navigation3 + Material3 adaptive
- **daemon**：Rust（android-binder + tokio + rustls + socket2 crate）+ AIDL（com.xingkong.monitor.shared.ipc.*）
- **helper.apk**：Kotlin（独立 APK，ADB 装，GPU 频率采样）
- **starter**：Kotlin 或 C++（app_process 拉 daemon）
- **构建**：Gradle 8.x + AGP 8.x + Cargo（NDK aarch64-linux-android target）

## 六、图标

`D:\Agent\icon\Xingkong-icons`（紫金黑玻璃拟态，金色旋钮表盘 + 四芒星）

## 七、离线运行

- 完全离线（无 Firebase、无统计、无远程配置）
- 崩溃日志写本地文件
- 自更新走 GitHub（保留 ghproxy 镜像）
