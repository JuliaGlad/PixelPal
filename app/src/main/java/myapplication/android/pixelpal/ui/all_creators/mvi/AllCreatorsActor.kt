package myapplication.android.pixelpal.ui.all_creators.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.pixelpal.domain.usecase.creators.GetGameCreatorsUseCase
import myapplication.android.pixelpal.ui.game_details.model.CreatorsGameUiList
import myapplication.android.pixelpal.ui.game_details.model.wrapper.toUi
import myapplication.android.pixelpal.ui.ktx.asyncAwait
import myapplication.android.pixelpal.ui.ktx.runCatchingNonCancellation
import myapplication.android.pixelpal.ui.mvi.MviActor

class AllCreatorsActor(
    private val getGameCreatorsUseCase: GetGameCreatorsUseCase
) : MviActor<
        AllCreatorsPartialState,
        AllCreatorsIntent,
        AllCreatorsState,
        AllCreatorsEffect>() {

    override fun resolve(
        intent: AllCreatorsIntent,
        state: AllCreatorsState
    ): Flow<AllCreatorsPartialState> =
        when (intent) {
            is AllCreatorsIntent.GetCreators -> loadCreators(intent.gameId, state.page + 1)
            AllCreatorsIntent.Init -> init()
        }

    private fun init() = flow { emit(AllCreatorsPartialState.Loading) }

    private fun loadCreators(gameId: Long, page: Int) =
        flow {
            kotlin.runCatching {
                getCreators(gameId, page)
            }.fold(
                onSuccess = { data ->
                    emit(AllCreatorsPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(AllCreatorsPartialState.Error(throwable))
                }
            )
        }

    private suspend fun getCreators(
        gameId: Long,
        page: Int
    ): CreatorsGameUiList =
        runCatchingNonCancellation {
            asyncAwait(
                { getGameCreatorsUseCase.invoke(gameId.toString(), page) }
            ) { data ->
                data.toUi()
            }
        }.getOrThrow()


}