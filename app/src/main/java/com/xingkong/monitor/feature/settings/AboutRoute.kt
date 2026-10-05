package com.xingkong.monitor.feature.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

/**
 * 关于软件页。
 *
 * 公益版规范（用户 2026-10-05 拍板）：
 *   - 名称：Xingkong（星控）
 *   - 特别鸣谢：Scene、Metric（参考项目，干净重写不复用二进制）
 *   - 贡献者：删除（不显示）
 *   - 开发者：极星（Pointers）
 *   - 联系方式：QQ 466620751、邮箱 466620751@qq.com、GitHub https://github.com/PointersMZX
 *   - Telegram：不显示（已删）
 *   - bilibili：https://space.bilibili.com/3546382782695708
 *   - 项目仓库：https://github.com/PointersMZX/Xingkong
 *   - 检查更新：见 UpdateChecker（待用户拍板更新模式后实现）
 *   - 更新日志：读 GitHub Release body（待实现）
 *
 * 无"支持项目维护"入口（公益版不收取费用，不做赞助引流）。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutRoute(onBack: () -> Unit) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("关于软件") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // 应用信息
            ListItem(
                headlineContent = { Text("名称") },
                supportingContent = { Text("Xingkong（星控）") }
            )
            ListItem(
                headlineContent = { Text("版本") },
                supportingContent = { Text("v1.0.0") }
            )
            ListItem(
                headlineContent = { Text("开发者") },
                supportingContent = { Text("极星（Pointers）") }
            )
            ListItem(
                headlineContent = { Text("特别鸣谢") },
                supportingContent = { Text("Scene、Metric（参考项目，干净重写不复用二进制）") }
            )
            ListItem(
                headlineContent = { Text("项目仓库") },
                supportingContent = { Text("github.com/PointersMZX/Xingkong") },
                modifier = Modifier.clickable {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/PointersMZX/Xingkong")))
                }
            )

            Spacer(Modifier.height(8.dp))
            Text("联系方式", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(start = 16.dp, top = 8.dp))

            ListItem(
                headlineContent = { Text("QQ") },
                supportingContent = { Text("466620751") }
            )
            ListItem(
                headlineContent = { Text("GitHub") },
                supportingContent = { Text("github.com/PointersMZX") },
                modifier = Modifier.clickable {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/PointersMZX")))
                }
            )
            ListItem(
                headlineContent = { Text("意见反馈") },
                supportingContent = { Text("466620751@qq.com") },
                modifier = Modifier.clickable {
                    context.startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:466620751@qq.com")))
                }
            )
            ListItem(
                headlineContent = { Text("bilibili") },
                supportingContent = { Text("space.bilibili.com/3546382782695708") },
                modifier = Modifier.clickable {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://space.bilibili.com/3546382782695708")))
                }
            )

            Spacer(Modifier.height(8.dp))
            Text("更新", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(start = 16.dp, top = 8.dp))

            // 检查更新（待实现，待用户拍板更新模式）
            ListItem(
                headlineContent = { Text("检查更新") },
                supportingContent = { Text("待实现（见下方说明）") }
            )
            ListItem(
                headlineContent = { Text("更新日志") },
                supportingContent = { Text("v1.0.0") }
            )

            Spacer(Modifier.height(16.dp))
            Text(
                "星控 v1.0.0 · 公益版\n完全离线运行，无任何云端上报",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
            )
        }
    }
}
