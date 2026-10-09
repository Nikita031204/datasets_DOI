package org.cross.cite

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.cross.cite.data.DoiRepositoryImpl
import org.cross.cite.detail.DoiDetailViewModelFactory
import org.cross.cite.domain.DoiRepository
import org.cross.cite.list.DoiListViewModelFactory
import org.cross.cite.resources.Res
import org.cross.cite.resources.action_toggle_language
import org.cross.cite.resources.action_toggle_theme
import org.cross.cite.resources.ic_theme
import org.cross.cite.resources.language_badge
import org.cross.cite.ui.components.AppScaffold
import org.cross.cite.ui.locale.AppLanguage
import org.cross.cite.ui.locale.ProvideAppLocale
import org.cross.cite.ui.navigation.AppNavDisplay
import org.cross.cite.ui.navigation.Navigator
import org.cross.cite.ui.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun App() {
    val systemDark = isSystemInDarkTheme()
    var darkTheme by remember { mutableStateOf(systemDark) }
    var language by remember { mutableStateOf(AppLanguage.fromSystem()) }

    val repository: DoiRepository = remember { DoiRepositoryImpl() }
    val navigator = remember { Navigator() }
    val listViewModelFactory = remember { DoiListViewModelFactory(navigator, repository) }
    val detailViewModelFactory = remember { DoiDetailViewModelFactory(navigator, repository) }
    val stateHolder = rememberSaveableStateHolder()

    AppTheme(darkTheme) {
        ProvideAppLocale(language) {
            stateHolder.SaveableStateProvider("app") {
                val backStack by navigator.backStack.collectAsStateWithLifecycle()
                AppScaffold(
                    onBack = navigator::back.takeIf { backStack.size > 1 },
                    actions = {
                        val languageDescription = stringResource(Res.string.action_toggle_language)
                        TextButton(
                            onClick = { language = language.next() },
                            modifier = Modifier.semantics {
                                contentDescription = languageDescription
                            },
                        ) {
                            Text(stringResource(Res.string.language_badge))
                        }
                        IconButton(onClick = { darkTheme = !darkTheme }) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_theme),
                                contentDescription = stringResource(Res.string.action_toggle_theme),
                            )
                        }
                    },
                ) { modifier ->
                    AppNavDisplay(
                        backStack = backStack,
                        onBack = navigator::back,
                        listViewModelFactory = listViewModelFactory,
                        detailViewModelFactory = detailViewModelFactory,
                        modifier = modifier,
                    )
                }
            }
        }
    }
}
