package org.cross.cite.ui.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.cross.cite.Screen

class Navigator {
    private val _backStack = MutableStateFlow<List<Screen>>(listOf(Screen.List))
    val backStack: StateFlow<List<Screen>> = _backStack.asStateFlow()

    fun open(screen: Screen) {
        _backStack.update { stack ->
            val existing = stack.indexOf(screen)
            if (existing >= 0) stack.take(existing + 1) else stack + screen
        }
    }

    fun back() {
        _backStack.update { stack -> if (stack.size > 1) stack.dropLast(1) else stack }
    }
}
