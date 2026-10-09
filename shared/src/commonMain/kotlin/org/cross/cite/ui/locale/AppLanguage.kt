package org.cross.cite.ui.locale

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.intl.Locale

enum class AppLanguage(val tag: String) {
    Ru("ru"),
    En("en");

    fun next(): AppLanguage = entries[(ordinal + 1) % entries.size]

    companion object {
        fun fromSystem(): AppLanguage =
            entries.firstOrNull { it.tag == Locale.current.language } ?: Ru
    }
}

@Composable
expect fun ProvideAppLocale(language: AppLanguage, content: @Composable () -> Unit)
