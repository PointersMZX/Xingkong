package com.xingkong.monitor.feature.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * 首次引导路由。
 *
 * 三页：介绍 / 启动服务（Root 启动 + Shell 命令启动）/ 校准（自动探测 + 串联双电芯 + 手动校准）。
 * 完成后 onDone 跳转 home。
 */
@Composable
fun OnboardingRoute(onDone: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("首次引导（占位 — 接力 4 实现）")
        Button(onClick = onDone) { Text("完成") }
    }
}
