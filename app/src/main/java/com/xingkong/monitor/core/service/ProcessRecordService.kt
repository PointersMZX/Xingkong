package com.xingkong.monitor.core.service

import androidx.lifecycle.LifecycleService

/**
 * 本地进程性能持续记录前台服务（specialUse FGS）。
 * TODO 接力 5：接 DaemonClient process-record 流，写 Room。
 */
class ProcessRecordService : LifecycleService()
