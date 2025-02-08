package myapplication.android.pixelpal.ui.creators

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.data.repository.publishers.PublishersRepository
import myapplication.android.pixelpal.domain.usecase.creators.GetCreatorsByQueryUseCase
import myapplication.android.pixelpal.domain.usecase.creators.GetCreatorsRolesUseCase
import myapplication.android.pixelpal.domain.usecase.publishers.GetPublishersByQueryUseCase
import myapplication.android.pixelpal.ui.creators.model.creatores.CreatorsUiList
import myapplication.android.pixelpal.ui.creators.model.creatores.toUi
import myapplication.android.pixelpal.ui.creators.model.publisher.toUi
import myapplication.android.pixelpal.ui.creators.model.roles.RolesUi
import myapplication.android.pixelpal.ui.creators.model.roles.toUi
import myapplication.android.pixelpal.ui.ktx.asyncAwait
import myapplication.android.pixelpal.ui.ktx.runCatchingNonCancellation
import java.util.stream.Collectors

@kotlinx.coroutines.ExperimentalCoroutinesApi
@OptIn(kotlinx.coroutines.FlowPreview::class)
class CreatorsViewModel @AssistedInject constructor(
    private val creatorsRepository: CreatorsRepository,
    private val publishersRepository: PublishersRepository
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
    private var _isQuerySame: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isQuerySame: StateFlow<Boolean>
        get() = _isQuerySame.asStateFlow()

    private val chosenRole: MutableStateFlow<Int> = MutableStateFlow(1)

    private val _roles: MutableSharedFlow<List<RolesUi>> = MutableSharedFlow(replay = 1)
    val roles = _roles.asSharedFlow()

    private val _searchCreatorsResult: MutableSharedFlow<CreatorsUiList?> =
        MutableSharedFlow(replay = 1)
    val searchCreatorsResult = _searchCreatorsResult.asSharedFlow()

    val searchQueryPublisher = MutableSharedFlow<String>(
        extraBufferCapacity = 1,
        replay = 1
    )

    init {
        loadRoles()
        listenToCreatorsQuery()
    }

    fun setChosenRole(roleId: Int) {
        chosenRole.value = roleId
    }

    private fun listenToCreatorsQuery() {
        searchQueryPublisher
            .filter { it.isNotEmpty() }
            .debounce(500)
            .mapLatest { query -> search(chosenRole.value, query) }
            .flowOn(Dispatchers.Default)
            .onEach { _searchCreatorsResult.emit(it) }
            .launchIn(viewModelScope)
    }

    private suspend fun search(roleId: Int, query: String): CreatorsUiList? {
        updateQueryVariable(query)
        return if (roleId != CreatorsFragment.PUBLISHER_ID) searchCreators(roleId, query)
        else searchPublishers(query)
    }


    private suspend fun searchCreators(roleId: Int, query: String): CreatorsUiList? =
        runCatchingNonCancellation {
            asyncAwait(
                { GetCreatorsByQueryUseCase(creatorsRepository).invoke(queryPage, roleId, query) }
            ) { data ->
                data.toUi()
            }
        }.getOrNull()


    private suspend fun searchPublishers(query: String): CreatorsUiList? =
        runCatchingNonCancellation {
            asyncAwait(
                { GetPublishersByQueryUseCase(publishersRepository).invoke(queryPage, query) }
            ) { data ->
                data.toUi()
            }
        }.getOrNull()


    private fun updateQueryVariable(query: String) {
        if (query == previousQuery) {
            queryPage++
            _isQuerySame.value = true
        } else {
            queryPage = 1
            previousQuery = query
            _isQuerySame.value = false
        }
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