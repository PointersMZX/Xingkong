# 星控（Xingkong）v1.0.0

Android 专业级性能监视工具，App + Daemon 双组件运行。用于查看设备性能状态、管理悬浮窗监视器、记录帧率相关会话数据。

## 项目定位

- **公益版**：会员功能全部开放，免费，不收取任何费用
- **完全离线**：无 Firebase、无广告归因、无远程配置，崩溃日志写本地文件
- **来源**：参考废弃项目 Metric v1.7.6（com.itos.metric.monitor）的形态与功能，干净重写

## 架构

三层特权架构（Shizuku 风格，保留原设计）：

| 层 | 组件 | 权限身份 | 作用 |
|---|---|---|---|
| App 主体 | `com.xingkong.monitor`（Kotlin Compose） | 普通 UID | UI、悬浮窗、记录管理、磁贴、前台服务 |
| Daemon | `libxingkong_daemon.so`（Rust） | root(0) 或 shell(2000) | CPU/GPU/内存/DDR/FPS 采样 + Perfetto trace |
| Helper APK | `com.xingkong.helper`（Kotlin） | shell/system（ADB 装） | GPU 频率节点采样 |

启动方式：
- Root 启动：root 设备直接 su 执行 daemon
- Shell 命令启动：ADB Shell 粘贴 App 显示的命令（含 native lib 路径）
- Helper 安装：`pm install -r -t --bypass-low-target-sdk-block /data/local/tmp/xingkong-helper.apk`

## 项目结构

```
Xingkong/
├── app/                # 主 App（Kotlin Compose + Hilt + Room + DataStore）
├── helper/             # helper.apk（Kotlin，ADB 装，GPU 频率）
├── shared-aidl/        # 共享 AIDL（IDaemonMain + 7 回调）
├── daemon/             # Rust daemon crate（Cargo）
├── docs/               # 离线文档（docsify）
├── PRD.md              # 产品需求
├── DEV_PLAN.md         # 开发计划（多轮接力基准）
├── ANALYSIS.md         # 参考包逆向分析 Brief
└── README.md           # 本文件
```

## 开发

### 环境要求

- JDK 21（`D:\Agent\tools\jdk21`）
- Android NDK r27c（`D:\Agent\tools\android-ndk-r27c`）
- Rust stable + aarch64-linux-android target（`D:\Agent\tools\cargo`）
- Android SDK Build-Tools 34.0.0+、Platform android-34

### 构建

```bash
# App + helper APK（Gradle）
./gradlew assembleDebug

# daemon native 库（Rust，需 NDK）
cd daemon && cargo build --release --target aarch64-linux-android
```

### 多轮接力

- 当前状态：阶段 0（环境装机）+ 阶段 1（项目骨架）进行中
- 详见 `DEV_PLAN.md` 的 8 个阶段
- 接棒前先读 `ANALYSIS.md` + `PRD.md` + `DEV_PLAN.md`

## 公益化要点

- 删除 membership 整个 feature 包（原 App GPU 频率卡会员，本版全开）
- 删除支付 deeplink + Alipay/Wxpay 支付链
- 删除 Firebase Analytics / RemoteConfig / Sessions / Crashlytics
- 删除 AD_ID / ACCESS_ADSERVICES 广告归因权限
- **保留** daemon + helper 特权架构（用户明确"无必要不违背原设计"）

## 图标

`D:\Agent\icon\Xingkong-icons`（紫金黑玻璃拟态，金色旋钮表盘 + 四芒星）

## 版本

- v1.0.0（开发中）
