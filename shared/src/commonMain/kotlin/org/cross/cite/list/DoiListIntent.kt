package org.cross.cite.list

sealed interface DoiListIntent {
    data class CardClicked(val doi: String) : DoiListIntent
    data class SearchQueryChanged(val query: String) : DoiListIntent
}
