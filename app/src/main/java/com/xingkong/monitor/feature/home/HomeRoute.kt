package com.xingkong.monitor.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * 首页路由。
 *
 * 公益版：GPU 指标默认全开（原 App 卡会员 GpuMembershipRequired，本版删除会员墙）。
 */
@Composable
fun HomeRoute(onOpenSettings: () -> Unit) {
    val vm: HomeViewModel = hiltViewModel()
    HomeScreen(state = vm.state.collectAsState().value, onOpenSettings = onOpenSettings)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(state: HomeUiState, onOpenSettings: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("星控") },
                actions = { TextButton(onClick = onOpenSettings) { Text("设置") } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MetricRow("Daemon 状态", if (state.daemonConnected) "已连接" else "未连接")
            MetricRow("CPU 占用", state.cpuUsageText)
            MetricRow("内存", state.memoryUsageText)
            MetricRow("GPU 占用", state.gpuUsageText)
            MetricRow("温度", state.thermalText)
            MetricRow("电池", state.batteryText)
        }
    }
}

@Composable
private fun MetricRow(label: String, value: String) {
    Text("$label：$value")
}

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    // TODO 接力 2：注入 DaemonClient，订阅 basic-metric 流，刷新 _state
}

data class HomeUiState(
    val daemonConnected: Boolean = false,
    val cpuUsageText: String = "--",
    val memoryUsageText: String = "--",
    val gpuUsageText: String = "--",
    val thermalText: String = "--",
    val batteryText: String = "--",
)
