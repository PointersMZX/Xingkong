package com.xingkong.helper.ui

import android.app.Activity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import com.xingkong.helper.sampler.GpuSamplerHolder

/**
 * Helper APK 启动 Activity。
 *
 * 显示当前 GPU 频率节点探测结果，便于用户排查（原 App 同款体验）。
 * Helper 通常无 UI，但作为 APK 必须有 LAUNCHER Activity 才能被 pm install。
 */
class HelperMainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sampler = GpuSamplerHolder.sampler
        val source = sampler.probeFrequencySource()
        val freq = sampler.readFrequencyMhz()
        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 48, 48, 48)
        }
        container.addView(TextView(this).apply { text = "Xingkong Helper v1.0.0"; textSize = 18f })
        container.addView(TextView(this).apply { text = "GPU 节点：${if (source.isEmpty()) "未找到" else source}" })
        container.addView(TextView(this).apply { text = "GPU 频率：${if (freq < 0) "--" else "$freq MHz"}" })
        container.addView(TextView(this).apply {
            text = "本 Helper 由星控 App 通过 ADB 装到特权位置，" +
                "在 shell/system UID 下运行以读取 GPU 频率节点。"
            textSize = 12f
        })
        setContentView(container)
    }
}
