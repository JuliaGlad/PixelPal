package myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.pixelpal.domain.usecase.stores.GetStoreDetailsUseCase
import myapplication.android.pixelpal.ui.ktx.asyncAwait
import myapplication.android.pixelpal.ui.ktx.runCatchingNonCancellation
import myapplication.android.pixelpal.ui.mvi.MviActor
import myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.model.toUi

class StoreDetailsActor(
    private val getStoreDetailsUseCase: GetStoreDetailsUseCase
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
            is StoreDetailsIntent.GetStoreDetails -> loadDetails(intent.id)
            StoreDetailsIntent.Init -> init()
        }

    private fun init() = flow { emit(StoreDetailsPartialState.Loading) }

    private fun loadDetails(id: Int) =
        flow {
            kotlin.runCatching {
                getStoreDetails(id)
            }.fold(
                onSuccess = { data ->
                    emit(StoreDetailsPartialState.DataLoaded(data))
                },
                onFailure = { error ->
                    emit(StoreDetailsPartialState.Error(error))
                }
            )
        }

    private suspend fun getStoreDetails(id: Int) =
        runCatchingNonCancellation {
            asyncAwait(
                { getStoreDetailsUseCase.invoke(id) }
            ){ data ->
                data.toUi()
            }
        }.getOrThrow()
}