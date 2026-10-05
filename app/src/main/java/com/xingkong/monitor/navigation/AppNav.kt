package com.xingkong.monitor.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xingkong.monitor.feature.home.HomeRoute
import com.xingkong.monitor.feature.onboarding.OnboardingRoute
import com.xingkong.monitor.feature.settings.SettingsRoute

/**
 * 顶层导航图。
 *
 * 路由：
 * - onboarding：首次引导（介绍/启动服务/校准三页），未完成且 Daemon 未连接时进入
 * - home：主界面（Daemon 已连接后）
 * - settings：设置
 *
 * 公益版：删除 membership 路由（原 App 的 MembershipRoute 全删）。
 */
object Routes {
    const val ONBOARDING = "onboarding"
    const val HOME = "home"
    const val SETTINGS = "settings"
}

@Composable
fun AppNav() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = Routes.HOME) {
        composable(Routes.ONBOARDING) { OnboardingRoute(onDone = { nav.navigate(Routes.HOME) { popUpTo(Routes.HOME) { inclusive = true } } }) }
        composable(Routes.HOME) { HomeRoute(onOpenSettings = { nav.navigate(Routes.SETTINGS) }) }
        composable(Routes.SETTINGS) { SettingsRoute(onBack = { nav.popBackStack() }) }
    }
}
