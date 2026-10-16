package org.cross.cite.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.cross.cite.Screen
import org.cross.cite.domain.DoiRepository
import org.cross.cite.ui.model.toDetailUi
import org.cross.cite.ui.navigation.Navigator

class DoiDetailViewModel(
    private val doi: String,
    private val navigator: Navigator,
    private val repository: DoiRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<DoiDetailState>(DoiDetailState.Loading)
    val state: StateFlow<DoiDetailState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val record = repository.getDoi(doi)
            _state.value = record
                ?.let { DoiDetailState.Content(it.toDetailUi()) }
                ?: DoiDetailState.NotFound(doi)
        }
    }

    fun onIntent(intent: DoiDetailIntent) {
        when (intent) {
            is DoiDetailIntent.RelatedClicked -> navigator.open(Screen.Detail(intent.doi))
        }
    }
}
