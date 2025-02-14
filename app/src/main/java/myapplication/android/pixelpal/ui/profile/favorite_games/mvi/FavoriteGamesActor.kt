package myapplication.android.pixelpal.ui.profile.favorite_games.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.pixelpal.domain.usecase.favorite_games.GetFavoriteGamesUseCase
import myapplication.android.pixelpal.ui.home.model.GamesMainInfoListUi
import myapplication.android.pixelpal.ui.home.model.toUi
import myapplication.android.pixelpal.ui.ktx.asyncAwait
import myapplication.android.pixelpal.ui.ktx.runCatchingNonCancellation
import myapplication.android.pixelpal.ui.mvi.MviActor

class FavoriteGamesActor(
    private val getFavoriteGamesUseCase: GetFavoriteGamesUseCase
) : MviActor<
        FavoriteGamesPartialState,
        FavoriteGamesIntent,
        FavoriteGamesState,
        FavoriteGamesEffect>() {

    override fun resolve(
        intent: FavoriteGamesIntent,
        state: FavoriteGamesState
    ): Flow<FavoriteGamesPartialState> =
        when (intent) {
            FavoriteGamesIntent.GetGames -> loadFavoriteGames()
            FavoriteGamesIntent.Init -> init()
        }

    private fun init() = flow { emit(FavoriteGamesPartialState.Init) }

    private fun loadFavoriteGames() =
        flow {
            kotlin.runCatching {
                getFavoriteGames()
            }.fold(
                onSuccess = { data ->
                    emit(FavoriteGamesPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(FavoriteGamesPartialState.Error(throwable))
                }
            )
        }

    private suspend fun getFavoriteGames(): GamesMainInfoListUi =
        runCatchingNonCancellation {
            asyncAwait(
                { getFavoriteGamesUseCase.invoke() }
            ){ data ->
                data.toUi()
            }
        }.getOrThrow()
}