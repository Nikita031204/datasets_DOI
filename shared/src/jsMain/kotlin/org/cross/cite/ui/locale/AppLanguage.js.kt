package org.cross.cite.ui.locale

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember

private fun setCustomLocale(tag: String) {
    js("window.__customLocale = tag")
}

@Composable
actual fun ProvideAppLocale(language: AppLanguage, content: @Composable () -> Unit) {
    remember(language) { setCustomLocale(language.tag) }
    key(language) { content() }
}
