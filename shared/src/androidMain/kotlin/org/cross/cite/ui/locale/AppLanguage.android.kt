package org.cross.cite.ui.locale

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import java.util.Locale

@Composable
actual fun ProvideAppLocale(language: AppLanguage, content: @Composable () -> Unit) {
    val base = LocalConfiguration.current
    val localized = remember(language, base) {
        val locale = Locale.forLanguageTag(language.tag)
        Locale.setDefault(locale)
        Configuration(base).apply { setLocale(locale) }
    }
    CompositionLocalProvider(LocalConfiguration provides localized) {
        key(language) { content() }
    }
}
