package myapplication.android.pixelpal.ui.games.games.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.pixelpal.domain.usecase.games.GetGameMonthReleasesUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGamesNewReleasesUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGamesShortDataUseCase
import myapplication.android.pixelpal.ui.games.games.model.toUi
import myapplication.android.pixelpal.ui.home.model.GamesNewsListUi
import myapplication.android.pixelpal.ui.home.model.toUi
import myapplication.android.pixelpal.ui.home.mvi.HomePartialState
import myapplication.android.pixelpal.ui.ktx.asyncAwait
import myapplication.android.pixelpal.ui.ktx.runCatchingNonCancellation
import myapplication.android.pixelpal.ui.mvi.MviActor

class GamesActor(
    private val getGamesShortDataUseCase: GetGamesShortDataUseCase
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
        }

    private fun init() = flow { emit(GamesPartialState.Loading) }

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

    private suspend fun getGamesShortData(id: Long, page: Int) =
        runCatchingNonCancellation {
            asyncAwait(
                { getGamesShortDataUseCase.invoke(page, id) }
            ) { games ->
                games.toUi()
            }
        }.getOrThrow()
}

