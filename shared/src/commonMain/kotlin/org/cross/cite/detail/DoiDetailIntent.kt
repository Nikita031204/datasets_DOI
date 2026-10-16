package org.cross.cite.detail

sealed interface DoiDetailIntent {
    data class RelatedClicked(val doi: String) : DoiDetailIntent
}
