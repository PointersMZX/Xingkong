package com.xingkong.monitor.feature.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.xingkong.monitor.core.helper.HelperInstaller

/**
 * 设置主页。
 *
 * 分类：启动 / 更新 / 采样 / 日志 / 社区 / 其他
 * 「其他」下含：关于软件、更新日志
 *
 * 公益版：无"支持项目维护"入口（不收取费用、不做赞助引流）。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsRoute(onBack: () -> Unit, onOpenAbout: () -> Unit) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("设置") },
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
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            // 启动
            SectionHeader("启动")
            ListItem(
                headlineContent = { Text("启动方式") },
                supportingContent = { Text("Root 启动 / ADB Shell 命令（待实现）") }
            )
            ListItem(
                headlineContent = { Text("安装 Helper（GPU 频率）") },
                supportingContent = { Text("导出 helper APK，生成 ADB 安装命令") },
                modifier = Modifier.clickable(onClick = {
                    val ctx = context
                    val cmd = HelperInstaller.buildAdbInstallCommand(ctx)
                    val cm = ctx.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    cm.setPrimaryClip(ClipData.newPlainText("xingkong-helper-adb", cmd))
                    Toast.makeText(ctx, "ADB 安装命令已复制\nhelper APK 已导出到 Download/xingkong-helper.apk", Toast.LENGTH_LONG).show()
                })
            )
            ListItem(
                headlineContent = { Text("开机自启") },
                supportingContent = { Text("已授权（待实现开关）") }
            )

            // 更新
            SectionHeader("更新")
            ListItem(
                headlineContent = { Text("自动检查更新") },
                supportingContent = { Text("待实现（待用户拍板更新模式）") }
            )

            // 采样
            SectionHeader("采样")
            ListItem(
                headlineContent = { Text("采样频率") },
                supportingContent = { Text("1 Hz（待实现）") }
            )

            // 日志
            SectionHeader("日志")
            ListItem(
                headlineContent = { Text("本地崩溃日志") },
                supportingContent = { Text("已启用（替代 Firebase Crashlytics）") }
            )

            // 社区
            SectionHeader("社区")
            ListItem(
                headlineContent = { Text("项目仓库") },
                supportingContent = { Text("github.com/PointersMZX/Xingkong") }
            )

            // 其他
            SectionHeader("其他")
            ListItem(
                headlineContent = { Text("关于软件") },
                trailingContent = { Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null) },
                modifier = Modifier.clickable(onClick = onOpenAbout)
            )
            ListItem(
                headlineContent = { Text("更新日志") },
                trailingContent = { Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null) }
            )
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        title,
        modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 4.dp)
    )
}
