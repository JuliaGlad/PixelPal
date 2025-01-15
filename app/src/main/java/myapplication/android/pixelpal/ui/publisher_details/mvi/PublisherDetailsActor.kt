package myapplication.android.pixelpal.ui.publisher_details.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.pixelpal.domain.usecase.games.GetGameByPublisherUseCase
import myapplication.android.pixelpal.domain.usecase.publishers.GetPublisherDetailsUseCase
import myapplication.android.pixelpal.ui.home.model.toUi
import myapplication.android.pixelpal.ui.ktx.asyncAwait
import myapplication.android.pixelpal.ui.ktx.runCatchingNonCancellation
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviActor
import myapplication.android.pixelpal.ui.publisher_details.model.PublisherDetailsResult
import myapplication.android.pixelpal.ui.publisher_details.model.toUi

class PublisherDetailsActor(
    private val publisherDetailsUseCase: GetPublisherDetailsUseCase,
    private val getGameByPublisherUseCase: GetGameByPublisherUseCase
): MviActor<
        PublisherDetailsPartialState,
        PublisherDetailsIntent,
        PublisherDetailsState,
        PublisherDetailsEffect>() {
    override fun resolve(
        intent: PublisherDetailsIntent,
        state: PublisherDetailsState
    ): Flow<PublisherDetailsPartialState> =
        when(intent){
            is PublisherDetailsIntent.GetPublisher -> loadDetails(intent.id, state.page + 1)
            PublisherDetailsIntent.Init -> init()
            is PublisherDetailsIntent.GetGames -> loadGames(
                (state.ui as LceState.Content).data,
                intent.id,
                state.page + 1)
        }

    private fun init() = flow { emit(PublisherDetailsPartialState.Loading) }

    private fun loadDetails(id: Long, page: Int) =
        flow{
            kotlin.runCatching {
                PublisherDetailsResult(
                    getPublisherDetails(id),
                    getGamesByPublisher(id, page)
                )
            }.fold(
                onSuccess = { data ->
                    emit(PublisherDetailsPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(PublisherDetailsPartialState.Error(throwable))
                }
            )
        }

    private fun loadGames(publisherDetailsResult: PublisherDetailsResult, id: Long, page: Int) =
        flow{
            kotlin.runCatching {
               getGamesByPublisher(id, page)
            }.fold(
                onSuccess = { data ->
                    val newItems = publisherDetailsResult.newItems
                    if (newItems != null) {
                        publisherDetailsResult.newItems!!.games.clear()
                        publisherDetailsResult.newItems = null
                    }
                    publisherDetailsResult.newItems = data
                    emit(PublisherDetailsPartialState.DataLoaded(publisherDetailsResult))
                },
                onFailure = { throwable ->
                    emit(PublisherDetailsPartialState.Error(throwable))
                }
            )
        }

    private suspend fun getGamesByPublisher(id: Long, page: Int) =
        runCatchingNonCancellation {
            asyncAwait(
                {getGameByPublisherUseCase.invoke(id, page)}
            ){ data ->
                data.toUi()
            }
        }.getOrThrow()

    private suspend fun getPublisherDetails(id: Long) =
        runCatchingNonCancellation {
            asyncAwait(
                {publisherDetailsUseCase.invoke(id)}
            ){ data ->
                data.toUi()
            }
        }.getOrThrow()
}