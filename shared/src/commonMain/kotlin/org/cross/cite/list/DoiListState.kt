package org.cross.cite.list

import androidx.compose.runtime.Immutable
import org.cross.cite.ui.model.DoiCardUi

@Immutable
data class DoiListState(
    val items: List<DoiCardUi> = emptyList(),
    val total: Int = 0,
)
