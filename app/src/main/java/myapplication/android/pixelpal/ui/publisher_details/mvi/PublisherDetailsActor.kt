package myapplication.android.pixelpal.ui.publisher_details.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.pixelpal.domain.usecase.publishers.GetPublisherDetailsUseCase
import myapplication.android.pixelpal.ui.ktx.asyncAwait
import myapplication.android.pixelpal.ui.ktx.runCatchingNonCancellation
import myapplication.android.pixelpal.ui.mvi.MviActor
import myapplication.android.pixelpal.ui.publisher_details.model.toUi

class PublisherDetailsActor(
    private val publisherDetailsUseCase: GetPublisherDetailsUseCase
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
            is PublisherDetailsIntent.GetPublisher -> loadDetails(intent.id)
            PublisherDetailsIntent.Init -> init()
        }

    private fun init() = flow { emit(PublisherDetailsPartialState.Loading) }

    private fun loadDetails(id: Long) =
        flow{
            kotlin.runCatching {
                getPublisherDetails(id)
            }.fold(
                onSuccess = { data ->
                    emit(PublisherDetailsPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(PublisherDetailsPartialState.Error(throwable))
                }
            )
        }

    private suspend fun getPublisherDetails(id: Long) =
        runCatchingNonCancellation {
            asyncAwait(
                {publisherDetailsUseCase.invoke(id)}
            ){ data ->
                data.toUi()
            }
        }.getOrThrow()
}