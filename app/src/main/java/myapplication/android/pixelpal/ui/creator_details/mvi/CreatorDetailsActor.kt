package myapplication.android.pixelpal.ui.creator_details.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.pixelpal.domain.usecase.creators.GetCreatorDetailsUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGameByCreatorUseCase
import myapplication.android.pixelpal.ui.creator_details.model.CreatorDetailsResultUi
import myapplication.android.pixelpal.ui.creator_details.model.toUi
import myapplication.android.pixelpal.ui.home.model.toUi
import myapplication.android.pixelpal.ui.ktx.asyncAwait
import myapplication.android.pixelpal.ui.ktx.runCatchingNonCancellation
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviActor

class CreatorDetailsActor(
    private val getCreatorDetailsUseCase: GetCreatorDetailsUseCase,
    private val getGameByCreatorUseCase: GetGameByCreatorUseCase
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
            is CreatorDetailsIntent.GetGamesByCreators -> loadGamesByCreator(
                (state.ui as LceState.Content).data,
                intent.id)
        }

    private fun loadGamesByCreator(creatorDetailsResultUi: CreatorDetailsResultUi, id: Long) =
        flow{
            kotlin.runCatching {
                getGames(id)
            }.fold(
                onSuccess = { data ->
                    creatorDetailsResultUi.games = data
                    emit(CreatorDetailsPartialState.GamesLoaded)
                },
                onFailure = { throwable ->
                    emit(CreatorDetailsPartialState.Error(throwable))
                }
            )
        }

    private fun loadDetails(id: Long) =
        flow {
            runCatchingNonCancellation {
                CreatorDetailsResultUi(
                    getDetails(id),
                    getGames(id)
                )
            }.fold(
                onSuccess = { data ->
                    emit(CreatorDetailsPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(CreatorDetailsPartialState.Error(throwable))
                }
            )
        }

    private suspend fun getGames(id: Long) =
        runCatchingNonCancellation {
            asyncAwait(
                { getGameByCreatorUseCase.invoke(id) }
            ) { data ->
                data.toUi()
            }
        }.getOrThrow()

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