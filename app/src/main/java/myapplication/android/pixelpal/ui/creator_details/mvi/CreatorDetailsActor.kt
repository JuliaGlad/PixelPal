package myapplication.android.pixelpal.ui.creator_details.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.pixelpal.domain.usecase.creators.GetCreatorDetailsUseCase
import myapplication.android.pixelpal.ui.creator_details.model.toUi
import myapplication.android.pixelpal.ui.ktx.asyncAwait
import myapplication.android.pixelpal.ui.ktx.runCatchingNonCancellation
import myapplication.android.pixelpal.ui.mvi.MviActor

class CreatorDetailsActor(
    private val getCreatorDetailsUseCase: GetCreatorDetailsUseCase
) : MviActor<
        CreatorDetailsPartialState,
        CreatorDetailsIntent,
        CreatorDetailsState,
        CreatorDetailsEffect>() {
    override fun resolve(
        intent: CreatorDetailsIntent,
        state: CreatorDetailsState
    ): Flow<CreatorDetailsPartialState> =
        when (intent) {
            is CreatorDetailsIntent.GetCreatorDetails -> loadDetails(intent.id)
            CreatorDetailsIntent.Init -> init()
        }


    private fun loadDetails(id: Long) =
        flow {
            runCatchingNonCancellation {
                getDetails(id)
            }.fold(
                onSuccess = { data ->
                    emit(CreatorDetailsPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(CreatorDetailsPartialState.Error(throwable))
                }
            )
        }

    private suspend fun getDetails(id: Long) =
        runCatchingNonCancellation {
            asyncAwait(
                { getCreatorDetailsUseCase.invoke(id) }
            ) { data ->
                data.toUi()
            }
        }.getOrThrow()

    private fun init() = flow { emit(CreatorDetailsPartialState.Loading) }
}