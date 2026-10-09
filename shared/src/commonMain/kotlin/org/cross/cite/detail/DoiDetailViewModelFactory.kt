package org.cross.cite.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import org.cross.cite.domain.DoiRepository
import org.cross.cite.ui.navigation.Navigator
import kotlin.reflect.KClass

class DoiDetailViewModelFactory(
    private val navigator: Navigator,
    private val repository: DoiRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        val doi = checkNotNull(extras[DoiKey]) { "doi is missing in CreationExtras" }
        @Suppress("UNCHECKED_CAST")
        return DoiDetailViewModel(doi = doi, navigator = navigator, repository = repository) as T
    }

    companion object {
        val DoiKey = CreationExtras.Key<String>()

        fun extrasFor(doi: String): CreationExtras =
            MutableCreationExtras().apply { set(DoiKey, doi) }
    }
}
