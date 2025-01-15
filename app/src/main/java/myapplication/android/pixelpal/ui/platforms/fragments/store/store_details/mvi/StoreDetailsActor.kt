package myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.pixelpal.domain.usecase.games.GetGamesByStoreUseCase
import myapplication.android.pixelpal.domain.usecase.stores.GetStoreDetailsUseCase
import myapplication.android.pixelpal.ui.home.model.toUi
import myapplication.android.pixelpal.ui.ktx.asyncAwait
import myapplication.android.pixelpal.ui.ktx.runCatchingNonCancellation
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviActor
import myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.model.StoreDetailsResult
import myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.model.toUi

class StoreDetailsActor(
    private val getStoreDetailsUseCase: GetStoreDetailsUseCase,
    private val getGamesByStoreUseCase: GetGamesByStoreUseCase
) : MviActor<
        StoreDetailsPartialState,
        StoreDetailsIntent,
        StoreDetailsState,
        StoreDetailsEffect>() {
    override fun resolve(
        intent: StoreDetailsIntent,
        state: StoreDetailsState
    ): Flow<StoreDetailsPartialState> =
        when (intent) {
            is StoreDetailsIntent.GetStoreDetails -> loadDetails(intent.id, state.page + 1)
            StoreDetailsIntent.Init -> init()
            is StoreDetailsIntent.GetGames -> loadGames(
                (state.ui as LceState.Content).data,
                intent.id,
                state.page + 1
            )
        }

    private fun init() = flow { emit(StoreDetailsPartialState.Loading) }

    private fun loadGames(storeDetailsResult: StoreDetailsResult, id: Int, page: Int) =
        flow {
            kotlin.runCatching {
                getGames(id, page)
            }.fold(
                onSuccess = { data ->
                    val newItems = storeDetailsResult.newItems
                    if (newItems != null) {
                        storeDetailsResult.newItems!!.games.clear()
                        storeDetailsResult.newItems = null
                    }
                    storeDetailsResult.newItems = data
                    emit(StoreDetailsPartialState.DataLoaded(storeDetailsResult))
                },
                onFailure = { throwable ->
                    emit(StoreDetailsPartialState.Error(throwable))
                }
            )
        }

    private fun loadDetails(id: Int, page: Int) =
        flow {
            kotlin.runCatching {
                StoreDetailsResult(
                    getStoreDetails(id),
                    getGames(id, page)
                )
            }.fold(
                onSuccess = { data ->
                    emit(StoreDetailsPartialState.DataLoaded(data))
                },
                onFailure = { error ->
                    emit(StoreDetailsPartialState.Error(error))
                }
            )
        }

    private suspend fun getGames(storeId: Int, page: Int) =
        runCatchingNonCancellation {
            asyncAwait(
                { getGamesByStoreUseCase.invoke(storeId, page) }
            ){ data ->
                data.toUi()
            }
        }.getOrThrow()

    private suspend fun getStoreDetails(id: Int) =
        runCatchingNonCancellation {
            asyncAwait(
                { getStoreDetailsUseCase.invoke(id) }
            ){ data ->
                data.toUi()
            }
        }.getOrThrow()
}