package com.github.rgbpx.chan.feature.main

import androidx.compose.runtime.Composable
import com.github.rgbpx.chan.app.di.AppScope
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.presenter.Presenter
import dev.zacsweers.metro.Inject

@Inject
@CircuitInject(MainScreen::class, AppScope::class)
class MainPresenter() : Presenter<MainScreen.State> {
    @Composable
    override fun present(): MainScreen.State {
        return MainScreen.State
    }
}
