package org.cross.cite.ui.locale

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import java.util.Locale

@Composable
actual fun ProvideAppLocale(language: AppLanguage, content: @Composable () -> Unit) {
    remember(language) { Locale.setDefault(Locale.forLanguageTag(language.tag)) }
    key(language) { content() }
}
