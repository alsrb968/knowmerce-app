@file:OptIn(ExperimentalMaterial3Api::class)

package com.knowmerce.app.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.knowmerce.app.ui.home.HomeScreen
import com.knowmerce.app.ui.splash.SplashScreen

@Composable
fun KnowmerceApp(
    appState: KnowmerceAppState = rememberKnowmerceAppState()
) {
//    if (appState.isOnline) {
        NavHost(
            modifier = Modifier,
            navController = appState.navController,
            startDestination = Screen.Home.route
        ) {
            composable(Screen.Splash.route) { backStackEntry ->
                SplashScreen(
                    navigateToHome = {
                        appState.navigateToHome(backStackEntry)
                    }
                )
            }

            composable(Screen.Home.route) { backstackEntry ->
                HomeScreen()
            }
        }
//    } else {
//        OfflineDialog { appState.refreshOnline() }
//    }
}

@Composable
fun OfflineDialog(onRetry: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = "인터넷 연결 오류") },
        text = { Text(text = "음악 정보 리스트를 가져올 수 없습니다.\n인터넷 연결을 확인 후 다시 시도해주세요.") },
        confirmButton = {
            TextButton(onClick = onRetry) {
                Text(text = "재시도")
            }
        }
    )
}
