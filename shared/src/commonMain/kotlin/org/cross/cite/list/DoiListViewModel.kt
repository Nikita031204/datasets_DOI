package org.cross.cite.list

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.cross.cite.Screen
import org.cross.cite.domain.DoiRepository
import org.cross.cite.ui.model.DoiCardUi
import org.cross.cite.ui.model.toCardsUi
import org.cross.cite.ui.navigation.Navigator

class DoiListViewModel(
    private val navigator: Navigator,
    private val repository: DoiRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(DoiListState())
    val state: StateFlow<DoiListState> = _state.asStateFlow()

    private val allItems = mutableStateListOf<DoiCardUi>()

    init {
        viewModelScope.launch {
            val records = repository.getDois()
            val cards = records.toCardsUi()
            allItems.addAll(cards)
            _state.update { it.copy(items = cards, total = records.size) }
        }
    }

    fun onIntent(intent: DoiListIntent) {
        when (intent) {
            is DoiListIntent.CardClicked -> navigator.open(Screen.Detail(intent.doi))
            is DoiListIntent.SearchQueryChanged -> handleSearchQuery(intent.query)
        }
    }

    private fun handleSearchQuery(query: String) {
        val filtered = if (query.isBlank()) {
            allItems.toList()
        } else {
            allItems.filter { it.title.contains(query, ignoreCase = true) }
        }
        _state.update { it.copy(searchQuery = query, items = filtered) }
    }
}
