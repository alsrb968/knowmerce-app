@file:OptIn(ExperimentalMaterial3Api::class)

package com.knowmerce.app.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.knowmerce.app.R
import com.knowmerce.app.ui.home.HomeScreen

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
        title = { Text(text = stringResource(R.string.network_error_title)) },
        text = { Text(text = stringResource(R.string.network_error_message)) },
        confirmButton = {
            TextButton(onClick = onRetry) {
                Text(text = stringResource(R.string.retry))
            }
        }
    )
}
