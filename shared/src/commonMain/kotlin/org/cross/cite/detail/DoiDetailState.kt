package org.cross.cite.detail

import androidx.compose.runtime.Immutable
import org.cross.cite.ui.model.DoiDetailUi

@Immutable
sealed interface DoiDetailState {
    data object Loading : DoiDetailState
    data class NotFound(val doi: String) : DoiDetailState
    data class Content(val record: DoiDetailUi) : DoiDetailState
}
