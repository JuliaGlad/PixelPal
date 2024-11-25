package myapplication.android.pixelpal.ui.creators

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import myapplication.android.pixelpal.di.DiContainer
import myapplication.android.pixelpal.ui.creators.model.roles.RolesUi
import myapplication.android.pixelpal.ui.creators.model.roles.toUi
import java.util.stream.Collectors

class CreatorsViewModel : ViewModel() {

    private val _roles: MutableSharedFlow<List<RolesUi>> = MutableSharedFlow(

    )
    val roles = _roles.asSharedFlow()

    init {
        getRoles()
    }

    private fun getRoles() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val roles =
                    DiContainer.creatorsRepository.getCreatorsRoles()
                        .stream()
                        .map { it.toUi() }
                        .collect(Collectors.toList())
                _roles.emit(roles)
            }
        }
    }
}