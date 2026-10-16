package org.cross.cite

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.cross.cite.resources.Res
import org.cross.cite.resources.app_title
import org.jetbrains.compose.resources.stringResource

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = stringResource(Res.string.app_title),
        state = rememberWindowState(width = 960.dp, height = 760.dp),
    ) {
        App()
    }
}
