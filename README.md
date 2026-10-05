# 星控（Xingkong）

Android 专业级性能监视工具，采用 App + Daemon 双组件运行，用于查看设备性能状态、管理悬浮窗监视器，并记录帧率相关会话数据。

## 它能做什么

- **实时性能**：CPU / 内存 / GPU / 温度 / 电池 / 功率
- **悬浮窗监视器**：在屏幕上浮动显示实时性能指标，可自由开关各字段
- **FPS 记录**：开始 / 停止 / 计时记录帧率，含帧时间、Jank、CPU / GPU / 线程负载
- **快捷磁贴**：下拉通知栏一键开始 / 停止 FPS 记录
- **历史会话**：保存并回看每次记录，含统计图表（最大 / 最小 / 平均 / 1% Low / 5% Low）与线程负载详情

## 安装

1. 从 [GitHub Release](https://github.com/PointersMZX/Xingkong/releases) 下载最新 APK：
   - **Xingkong_vX.X.X.apk** — 主 App（必须安装）
   - **Xingkong_Helper_vX.X.X.apk** — GPU 频率采样 Helper（可选，提升 GPU 指标可用性）
2. 安装主 App，按首次引导操作：
   - 选择 Daemon 启动方式：**Root 启动** 或 **复制 Shell 命令到 ADB Shell 执行**
   - 需要 GPU 频率指标时，按指引用 ADB 安装 Helper

## 使用

| 位置 | 功能 |
|---|---|
| 首页 | 实时性能卡片（内存 / GPU / CPU 聚合 / 占用最高进程） |
| 悬浮窗管理 | 开关各指标的悬浮显示、调整字段 |
| FPS 记录 | 打开目标 App → 开启记录 → 切回目标 App → 结束后到 FPS Records 查看 |
| FPS Records | 历史会话列表 / 详情（图表、统计、线程负载）/ 删除 / 长截图 / 导出 |
| 内存详情 | Swap / Zram / MemoryInfo |
| 设置 | 启动 / 更新 / 采样 / 日志 / 社区 / 关于软件 |
| 离线文档 | App 内置使用手册（设置 → 关于 → 查看文档） |

> 部分指标显示 `--` 不代表故障：不同设备、权限和内核节点会影响可读取的数据范围。

## 更新

- App 内置**检查更新**：从 GitHub Release 拉取最新版本
- **完全离线运行**：无统计、无广告、无云端数据上报

## 社区

| 渠道 | 入口 |
|---|---|
| QQ | 466620751 |
| GitHub | [github.com/PointersMZX](https://github.com/PointersMZX) |
| 意见反馈 | 466620751@qq.com |
| bilibili | [space.bilibili.com/3546382782695708](https://space.bilibili.com/3546382782695708) |

## 关于

- **名称**：Xingkong（星控）
- **开发者**：极星（Pointers）
- **特别鸣谢**：Scene、Metric
- 本应用完全离线运行，无任何云端数据上报
