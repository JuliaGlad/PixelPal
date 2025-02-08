package myapplication.android.pixelpal.ui.games.games.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.pixelpal.domain.usecase.games.GetGamesByQueryAndGenre
import myapplication.android.pixelpal.domain.usecase.games.GetGamesShortDataUseCase
import myapplication.android.pixelpal.ui.games.games.model.toUi
import myapplication.android.pixelpal.ui.ktx.asyncAwait
import myapplication.android.pixelpal.ui.ktx.runCatchingNonCancellation
import myapplication.android.pixelpal.ui.mvi.MviActor

class GamesActor(
    private val getGamesShortDataUseCase: GetGamesShortDataUseCase,
    private val getGamesByQueryAndGenre: GetGamesByQueryAndGenre
) : MviActor<
        GamesPartialState,
        GamesIntent,
        GamesState,
        GamesEffects>() {

    override fun resolve(
        intent: GamesIntent,
        state: GamesState
    )
            : Flow<GamesPartialState> =
        when (intent) {
            is GamesIntent.GetGames -> loadGamesShortData(intent.id, state.page + 1)
            GamesIntent.Init -> init()
            is GamesIntent.GetGamesByQuery -> loadGamesShortDataByQuery(
                intent.id,
                state.page + 1,
                intent.query
            )
        }

    private fun init() = flow { emit(GamesPartialState.Init) }

    private fun loadGamesShortData(id: Long, page: Int) =
        flow {
            kotlin.runCatching {
                getGamesShortData(id, page)
            }.fold(
                onSuccess = {
                    emit(GamesPartialState.DataLoaded(ui = it))
                },
                onFailure = { emit(GamesPartialState.Error(throwable = it)) }
            )
        }

    private fun loadGamesShortDataByQuery(id: Long, page: Int, query: String) =
        flow {
            kotlin.runCatching {
                getGamesByQuery(id, page, query)
            }.fold(
                onSuccess = { data ->
                    emit(GamesPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(GamesPartialState.Error(throwable))
                }
            )
        }

    private suspend fun getGamesByQuery(id: Long, page: Int, query: String) =
        runCatchingNonCancellation {
            asyncAwait(
                { getGamesByQueryAndGenre.invoke(page, id, query) }
            ){ games ->
                games.toUi()
            }
        }.getOrThrow()

    private suspend fun getGamesShortData(id: Long, page: Int) =
        runCatchingNonCancellation {
            asyncAwait(
                { getGamesShortDataUseCase.invoke(page, id) }
            ) { games ->
                games.toUi()
            }
        }.getOrThrow()
}

