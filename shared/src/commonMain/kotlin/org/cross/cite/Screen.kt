package org.cross.cite

sealed interface Screen {
    data object List : Screen
    data class Detail(val doi: String) : Screen
}
