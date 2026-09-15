package com.github.rgbpx.chan

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

import chan.shared.generated.resources.Res
import chan.shared.generated.resources.compose_multiplatform
import com.github.rgbpx.chan.settings.AppSettingsRepository
import com.github.rgbpx.chan.ui.LoadingScreen
import com.github.rgbpx.chan.ui.MainScreen
import com.github.rgbpx.chan.ui.OnboardingScreen

@Composable
fun App(appSettingsRepository: AppSettingsRepository) {
    val settings by appSettingsRepository.settings.collectAsState(initial = null)
    val scope = rememberCoroutineScope()

    when (val currentSettings = settings) {
        null -> LoadingScreen()
        else -> if (currentSettings.firstLaunch) {
            OnboardingScreen(onFinished = {
                scope.launch { appSettingsRepository.setFirstLaunchCompleted() }
            })
        } else {
            MainScreen()
        }
    }
}

//@Preview
//@Composable
//fun App(appSettingsRepository: AppSettingsRepository) {
//    val settings by appSettingsRepository.settings.collectAsState(initial = null)
//    val scope = rememberCoroutineScope()
//
//    val currentSettings = settings ?: return // nothing loaded from disk yet — show nothing/a splash here
//
////    if (currentSettings.firstLaunch) {
////        OnboardingScreen(
////            onFinished = {
////                scope.launch { appSettingsRepository.setFirstLaunchCompleted() }
////            }
////        )
////    } else {
////        MainScreen()
////    }
//
//
//    MaterialTheme {
//        var showContent by remember { mutableStateOf(false) }
//        Column(
//            modifier = Modifier
//                .background(MaterialTheme.colorScheme.primaryContainer)
//                .safeContentPadding()
//                .fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//        ) {
//            Button(onClick = { showContent = !showContent }) {
//                Text("Click me!")
//            }
//            AnimatedVisibility(showContent) {
//                val greeting = remember { Greeting().greet() }
//                Column(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                ) {
//                    Image(painterResource(Res.drawable.compose_multiplatform), null)
//                    Text("Compose: $greeting")
//                }
//            }
//
//            if (currentSettings.firstLaunch) {
//                Text("First")
//            } else {
//                Text("Not first")
//            }
//        }
//    }
//}
