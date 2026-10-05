package com.xingkong.helper.ipc

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.xingkong.helper.HelperServiceImpl
import com.xingkong.helper.sampler.GpuSamplerHolder

/**
 * HelperBinderProvider：daemon 持 system/shell 权限调用 call("getHelperBinder")，
 * 拿到 IMetricHelper binder，从而读到 GPU 频率节点。
 *
 * authorities = "com.xingkong.helper.binderProvider"
 * exported = true（无 permission 限制，但 daemon 持 INTERACT_ACROSS_USERS_FULL 才能跨用户调）
 *
 * 与原 App com.itos.metric.helper.ipc.HelperBinderProvider 同构。
 */
class HelperBinderProvider : ContentProvider() {

    private var serviceImpl: HelperServiceImpl? = null

    override fun onCreate(): Boolean {
        serviceImpl = HelperServiceImpl()
        Log.i(TAG, "HelperBinderProvider ready, GPU source=${GpuSamplerHolder.sampler.probeFrequencySource()}")
        return true
    }

    override fun call(method: String, arg: String?, extras: Bundle?): Bundle? {
        if (method == "getHelperBinder") {
            val binder: IBinder = serviceImpl ?: return null
            val out = Bundle()
            out.putBinder("helper", binder)
            return out
        }
        return super.call(method, arg, extras)
    }

    // 以下空实现
    override fun query(uri: Uri, p: Array<out String>?, s: String?, sa: Array<out String>?, so: String?): Cursor? = null
    override fun getType(uri: Uri): String? = null
    override fun insert(uri: Uri, v: ContentValues?): Uri? = null
    override fun delete(uri: Uri, s: String?, sa: Array<out String>?): Int = 0
    override fun update(uri: Uri, v: ContentValues?, s: String?, sa: Array<out String>?): Int = 0

    companion object {
        private const val TAG = "Xingkong/HelperProvider"
    }
}
