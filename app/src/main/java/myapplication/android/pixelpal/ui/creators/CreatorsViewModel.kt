package myapplication.android.pixelpal.ui.creators

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.domain.model.creator.RoleDomain
import myapplication.android.pixelpal.domain.usecase.creators.GetCreatorsByQueryUseCase
import myapplication.android.pixelpal.domain.usecase.creators.GetCreatorsRolesUseCase
import myapplication.android.pixelpal.ui.creators.model.creatores.CreatorsUiList
import myapplication.android.pixelpal.ui.creators.model.creatores.toUi
import myapplication.android.pixelpal.ui.creators.model.roles.RolesUi
import myapplication.android.pixelpal.ui.creators.model.roles.toUi
import myapplication.android.pixelpal.ui.ktx.asyncAwait
import myapplication.android.pixelpal.ui.ktx.runCatchingNonCancellation
import java.util.stream.Collectors

@kotlinx.coroutines.ExperimentalCoroutinesApi
@OptIn(kotlinx.coroutines.FlowPreview::class)
class CreatorsViewModel @AssistedInject constructor(
    private val creatorsRepository: CreatorsRepository
) : ViewModel() {

    @AssistedFactory
    interface CreatorsViewModelAssistedFactory {
        fun create(): CreatorsViewModel
    }

    class Factory(
        private val factory: CreatorsViewModelAssistedFactory
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return factory.create() as T
        }
    }

    private var queryPage = 1
    private var previousQuery = ""

    //  var isQuerySame = false
    private var _isQuerySame: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isQuerySame: StateFlow<Boolean>
        get() = _isQuerySame.asStateFlow()

    private val chosenRole: MutableStateFlow<Int> = MutableStateFlow(1)

    private val _roles: MutableSharedFlow<List<RolesUi>> = MutableSharedFlow(replay = 1)
    val roles = _roles.asSharedFlow()

    private val _searchResult: MutableSharedFlow<CreatorsUiList?> = MutableSharedFlow(replay = 1)
    val searchResult = _searchResult.asSharedFlow()

    val searchQueryPublisher = MutableSharedFlow<String>(
        extraBufferCapacity = 1,
        replay = 1
    )

    init {
        loadRoles()
        listenToQuery()
    }

    fun setChosenRole(roleId: Int) {
        chosenRole.value = roleId
    }

    private fun listenToQuery() {
        searchQueryPublisher
            .filter { it.isNotEmpty() }
            .debounce(500)
            .mapLatest { query -> search(chosenRole.value, query) }
            .flowOn(Dispatchers.Default)
            .onEach { _searchResult.emit(it) }
            .launchIn(viewModelScope)
    }

    private suspend fun search(roleId: Int, query: String): CreatorsUiList? {
        updateQueryVariable(query)
        return runCatchingNonCancellation {
            asyncAwait(
                { GetCreatorsByQueryUseCase(creatorsRepository).invoke(queryPage, roleId, query) }
            ) { data ->
                data.toUi()
            }
        }.getOrNull()
    }

    private fun updateQueryVariable(query: String) {
        Log.i("Variables query", "$query $previousQuery")
        if (query == previousQuery) {
            queryPage++
            _isQuerySame.value = true
        } else {
            queryPage = 1
            previousQuery = query
            _isQuerySame.value = false
        }
        Log.i("Variables", queryPage.toString() + " " + previousQuery + " " + _isQuerySame.value)
    }

    private suspend fun getRoles(): List<RolesUi>? =
        runCatchingNonCancellation {
            asyncAwait(
                { GetCreatorsRolesUseCase(creatorsRepository).invoke() }
            ) { data ->
                data.stream()
                    .map { it.toUi() }
                    .collect(Collectors.toList())
            }
        }.getOrNull()


    private fun loadRoles() {
        viewModelScope.launch {
            getRoles()?.let { _roles.emit(it) }
        }
    }
}