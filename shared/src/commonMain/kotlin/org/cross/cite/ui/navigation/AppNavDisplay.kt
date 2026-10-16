package org.cross.cite.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import org.cross.cite.Screen
import org.cross.cite.detail.DoiDetailViewModel
import org.cross.cite.detail.DoiDetailViewModelFactory
import org.cross.cite.list.DoiListViewModel
import org.cross.cite.list.DoiListViewModelFactory
import org.cross.cite.ui.screens.detail.DoiDetailScreen
import org.cross.cite.ui.screens.list.DoiListScreen

private const val TRANSITION_MS = 300

@Composable
fun AppNavDisplay(
    backStack: List<Screen>,
    onBack: () -> Unit,
    listViewModelFactory: DoiListViewModelFactory,
    detailViewModelFactory: DoiDetailViewModelFactory,
    modifier: Modifier = Modifier,
) {
    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        onBack = onBack,
        transitionSpec = { slide(SlideDirection.Start) },
        popTransitionSpec = { slide(SlideDirection.End) },
        predictivePopTransitionSpec = { slide(SlideDirection.End) },
        entryProvider = entryProvider {
            entry<Screen.List> {
                val viewModel: DoiListViewModel = viewModel(factory = listViewModelFactory)
                val state by viewModel.state.collectAsStateWithLifecycle()
                DoiListScreen(state = state, onIntent = viewModel::onIntent)
            }
            entry<Screen.Detail> { key ->
                val viewModel: DoiDetailViewModel = viewModel(
                    key = "detail-${key.doi}",
                    factory = detailViewModelFactory,
                    extras = DoiDetailViewModelFactory.extrasFor(key.doi),
                )
                val state by viewModel.state.collectAsStateWithLifecycle()
                DoiDetailScreen(state = state, onIntent = viewModel::onIntent)
            }
        },
    )
}

private fun AnimatedContentTransitionScope<*>.slide(direction: SlideDirection): ContentTransform =
    (slideIntoContainer(direction, tween(TRANSITION_MS)) + fadeIn(tween(TRANSITION_MS)))
        .togetherWith(
            slideOutOfContainer(direction, tween(TRANSITION_MS)) + fadeOut(tween(TRANSITION_MS)),
        )
