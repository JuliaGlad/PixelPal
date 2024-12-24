package myapplication.android.pixelpal.ui.platforms.fragments.store.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.pixelpal.domain.usecase.stores.GetStoresUseCase
import myapplication.android.pixelpal.ui.ktx.asyncAwait
import myapplication.android.pixelpal.ui.ktx.runCatchingNonCancellation
import myapplication.android.pixelpal.ui.mvi.MviActor
import myapplication.android.pixelpal.ui.platforms.fragments.store.model.StoresUiList
import myapplication.android.pixelpal.ui.platforms.fragments.store.model.toUi

class StoresActor(
    private val getStoresUseCase: GetStoresUseCase
) : MviActor<
        StoresPartialState,
        StoresIntent,
        StoresState,
        StoresEffect
        >() {
    override fun resolve(
        intent: StoresIntent,
        state: StoresState
    ): Flow<StoresPartialState> =
        when (intent) {
            StoresIntent.GetStores -> loadStores(state.page + 1)
            StoresIntent.Init -> init()
        }

    private fun init() =
        flow {
            emit(StoresPartialState.Loading)
        }

    private fun loadStores(page: Int): Flow<StoresPartialState> =
        flow {
            kotlin.runCatching {
                getStores(page)
            }.fold(
                onSuccess = { data ->
                    emit(
                        StoresPartialState.DataLoaded(data)
                    )
                },
                onFailure = { exception ->
                    emit(StoresPartialState.Error(exception))
                }
            )
        }

    private suspend fun getStores(page: Int): StoresUiList =
        runCatchingNonCancellation {
            asyncAwait(
                { getStoresUseCase.invoke(page) }
            ) { stores ->
                stores.toUi()
            }
        }.getOrThrow()
}