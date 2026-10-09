package com.github.rgbpx.chan.imageboard.site.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.rgbpx.chan.app.di.AppScope
import com.slack.circuit.codegen.annotations.CircuitInject
import dev.zacsweers.metro.Inject

@CircuitInject(NewSiteScreen::class, AppScope::class)
@Inject
@Composable
fun NewSiteUi(
    state: NewSiteScreen.State,
    modifier: Modifier = Modifier,
) {
    SiteForm(
        name = state.name,
        baseUrl = state.baseUrl,
        engineId = state.engineId,
        engineIds = state.engineIds,
        baseUrlError = state.baseUrlError,
        canSave = state.canSave,
        onNameChanged = {
            state.eventSink(
                NewSiteScreen.Event.NameChanged(it)
            )
        },
        onBaseUrlChanged = {
            state.eventSink(
                NewSiteScreen.Event.BaseUrlChanged(it)
            )
        },
        onEngineSelected = {
            state.eventSink(
                NewSiteScreen.Event.EngineSelected(it)
            )
        },
        onSaveClicked = {
            state.eventSink(
                NewSiteScreen.Event.SaveClicked
            )
        },
        onCancelClicked = {
            state.eventSink(
                NewSiteScreen.Event.CancelClicked
            )
        },
        modifier = modifier,
    )
}
