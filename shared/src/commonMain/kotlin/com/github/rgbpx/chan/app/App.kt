package com.github.rgbpx.chan.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.github.rgbpx.chan.app.di.AppGraph
import com.github.rgbpx.chan.feature.bootstrap.BootstrapScreen
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import dev.zacsweers.metro.createGraph

@Composable
fun App(
    appGraph: AppGraph = remember {
        createGraph()
    },
    onExitRequest: () -> Unit,
) {
    CircuitCompositionLocals(
        appGraph.circuit,
        appGraph.circuitSaver,
    ) {
        val backStack = rememberSaveableBackStack(root = BootstrapScreen)
        val navigator = rememberCircuitNavigator(
            backStack,
            onRootPop = {
                onExitRequest()
            }
        )

        NavigableCircuitContent(navigator = navigator, backStack = backStack)
    }
}
