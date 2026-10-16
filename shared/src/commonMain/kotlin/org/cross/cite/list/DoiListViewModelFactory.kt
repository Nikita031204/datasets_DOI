package org.cross.cite.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import org.cross.cite.domain.DoiRepository
import org.cross.cite.ui.navigation.Navigator
import kotlin.reflect.KClass

class DoiListViewModelFactory(
    private val navigator: Navigator,
    private val repository: DoiRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        @Suppress("UNCHECKED_CAST")
        return DoiListViewModel(navigator = navigator, repository = repository) as T
    }
}
