package com.xingkong.monitor.ipc

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

/**
 * BinderProvider：daemon 启动后通过此 Provider 把 Binder 推回 App。
 *
 * authorities = "com.xingkong.monitor.binderProvider"
 * permission  = INTERACT_ACROSS_USERS_FULL（daemon 持 system/shell 权限才能调）
 *
 * 机制：daemon 调 call("attachBinder", extras=Binder)，App 持有后订阅数据流。
 * 这是 Shizuku 风格的 Binder 推回通道（与原 App com.itos.metric.monitor.ipc.BinderProvider 同构）。
 *
 * TODO 接力 2：实现 call() 拦截 attachBinder 动作，缓存 Binder 并通知 DaemonClient。
 */
class BinderProvider : ContentProvider() {
    override fun onCreate(): Boolean = true

    override fun query(uri: Uri, p: Array<out String>?, s: String?, sa: Array<out String>?, so: String?): Cursor? = null
    override fun getType(uri: Uri): String? = null
    override fun insert(uri: Uri, v: ContentValues?): Uri? = null
    override fun delete(uri: Uri, s: String?, sa: Array<out String>?): Int = 0
    override fun update(uri: Uri, v: ContentValues?, s: String?, sa: Array<out String>?): Int = 0
    override fun call(method: String, arg: String?, extras: android.os.Bundle?): android.os.Bundle? {
        // TODO 接力 2：method == "attachBinder" 时 extras 取 IBinder，转 DaemonClient
        return super.call(method, arg, extras)
    }
}
