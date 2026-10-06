# 内存详情

## 入口

在首页点击内存卡片进入内存详情页。

## 这个页面适合看什么

内存详情页展示当前设备的内存快照。  
它适合确认：

- Swap 是否启用。
- Zram 是否正常工作。
- VM 参数当前是什么值。
- `/proc/meminfo` 中关键字段的实时状态。

它不适合看历史趋势。  
如果要分析时间段变化，需要结合其他记录能力。

## 页面分区

- Swap：查看交换空间容量和占用。
- Zram：查看压缩内存容量、算法和占用。
- VM parameters：查看虚拟内存相关参数。
- 启用的 Swap：查看系统当前启用的 Swap 设备或文件。
- Zram 状态：查看压缩率、原始大小、物理占用等。
- SWAP IO：查看 Swap 读写累计值。
- MemoryInfo：展示 `/proc/meminfo` 字段。

## Swap

Swap 区域用于判断系统是否启用了交换空间。

如果无数据：

- 设备可能没有启用 Swap。
- 当前权限可能无法读取。
- 内核没有暴露对应信息。

无数据时页面会保留结构，不会隐藏整个区域。

## Zram

Zram 是常见的压缩内存方案。

重点看：

- 容量是否符合预期。
- 压缩算法是否正确。
- 原始数据大小和压缩后大小差异。
- 物理内存占用是否异常。
- 压缩率是否过低。

如果页面提供 Zram 调整入口，请确认你知道设备当前内存策略。  
不建议在不了解影响时随意重建 Zram。

## VM parameters

常见字段：

- `swappiness`：影响系统使用 Swap 的倾向。
- `watermark_scale_factor`：影响内存水位和回收行为。

这些参数会影响系统内存回收策略。  
调参前建议记录原值，避免难以回退。

## MemoryInfo

MemoryInfo 直接使用内核字段名，不翻译。  
这样方便和 `/proc/meminfo` 对照。

常见字段：

- `MemTotal`
- `MemFree`
- `MemAvailable`
- `Cached`
- `SwapTotal`
- `SwapFree`
- `AnonPages`
- `Mapped`
- `Shmem`
- `Slab`
- `KernelStack`
- `PageTables`
- `VmallocUsed`
- `ZramCompr`

不同内核版本返回的字段可能不同。  
Metric 按实际读取结果展示。
