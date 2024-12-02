package myapplication.android.pixelpal.ui.creators

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.ui.creators.model.roles.RolesUi
import myapplication.android.pixelpal.ui.creators.model.roles.toUi
import java.util.stream.Collectors

class CreatorsViewModel  @AssistedInject constructor(
    private val creatorsRepository: CreatorsRepository
) : ViewModel() {

    @AssistedFactory
    interface CreatorsViewModelAssistedFactory{
        fun create(): CreatorsViewModel
    }

    class Factory(
        private val factory: CreatorsViewModelAssistedFactory
    ) : ViewModelProvider.Factory{

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return factory.create() as T
        }

    }

    private val _roles: MutableSharedFlow<List<RolesUi>> = MutableSharedFlow()
    val roles = _roles.asSharedFlow()

    init {
        getRoles()
    }

    private fun getRoles() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val roles =
                    creatorsRepository.getCreatorsRoles()
                        .stream()
                        .map { it.toUi() }
                        .collect(Collectors.toList())
                _roles.emit(roles)
            }
        }
    }
}