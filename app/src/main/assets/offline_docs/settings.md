# 设置项

## 入口

从顶层导航进入设置页。

设置项会影响 App 展示、Daemon 启动、校准、FPS 采样和日志输出。  
如果正在录制 FPS，部分采样相关设置可能不可修改。

## General

- Theme settings：切换 App 主题。
- Boot auto-start(ROOT)：设备开机后尝试 Root 自启动服务。
- Hide from recents：从最近任务中隐藏 App。

Boot auto-start(ROOT) 只适用于 Root 场景。  
如果 Root 授权失败，开机自启也不会成功。

## Updates

- Check for updates on startup：App 启动后自动检查更新。
- Update channel：选择更新通道。

关闭启动检查后，App 不会在启动时主动检查新版本。  
你仍可以在对应入口手动检查。

## Battery

- Serial dual-cell battery：修正双电芯设备的功率显示。
- Current calibration：调整电流读数倍率。

这些设置会影响所有 Power 展示。  
修改前建议先看 [电池与功率校准](calibration.md)。

## Sampling

- FPS sampling：选择实时 FPS 采样来源。
- Precise sampling：提高采样精度，但增加开销。
- Tap to stop FPS recording：录制中点击胶囊直接停止。
- Hide FPS detail overlay：点击后直接开始记录，不展开详情悬浮层。

如果 FPS 获取不到，可以优先检查 FPS sampling。  
Precise sampling 不建议长期无脑开启。

## Log

日志设置用于排查问题。

常见项目：

- 日志保留天数。
- 日志级别。

日志级别会影响 App、Server 和 native 侧日志输出。  
调试时可以临时提高日志详细程度，问题确认后建议恢复默认级别。

## Other

Other 分组放置不属于上述类别的入口。  
具体项目以当前 App 版本展示为准。

## Metric Pro

Metric Pro 用于解锁部分高级能力。  
如果某个功能需要会员状态，页面会显示对应提示。
