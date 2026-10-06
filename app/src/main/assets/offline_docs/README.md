# Metric 使用手册

Metric 依赖独立的 Metric Daemon 读取系统性能数据。  
首次使用时，先启动服务并完成电池与功率校准，再使用悬浮窗、FPS 记录和内存详情等功能。

## 首次使用

按下面顺序完成初始化：

1. [快速开始](getting-started.md)
2. [启动服务与激活页](startup-guide.md)
3. [电池与功率校准](calibration.md)

## 功能说明

- [悬浮窗](overlays.md)
- [FPS 记录](fps-record.md)
- [内存详情](memory.md)
- [设置项](settings.md)

## 异常处理

- [排查问题](troubleshooting.md)

## 运行方式

Metric 分成两部分：

- App：负责界面、设置、悬浮窗和历史记录。
- Metric Daemon：负责读取系统性能数据。

App 必须连接到 Metric Daemon 后，才能显示首页数据、开启悬浮窗和开始 FPS 记录。  
如果 Daemon 没有连接，App 会显示激活页，而不是展示空仪表盘。

## 主要入口

- 首页：查看内存、GPU、CPU、进程等实时状态。
- 功能页：进入 FPS Records 和进程管理。
- 悬浮窗管理：开启负载监视器、线程监视器和 FPS 记录器。
- 设置页：调整主题、启动、更新、校准、采样和日志。
- 内存卡片：进入内存详情页。

## 重要规则

- 启动命令以 App 页面显示为准，不要手动拼接路径。
- 首次引导完成不代表 Daemon 永久在线；Daemon 断开后仍会回到激活页。
- 功率显示异常时，先处理校准，再判断其他功能是否异常。
- FPS 记录绑定当前前台 App；前台 App 变化会停止并保存记录。
- 指标不可用时，Metric 会显示占位或异常状态，不会用模拟数据补齐。
