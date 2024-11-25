package myapplication.android.pixelpal.ui.games.main.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.pixelpal.domain.usecase.genres.GetGenreDescriptionUseCase
import myapplication.android.pixelpal.domain.usecase.genres.GetGenresUseCase
import myapplication.android.pixelpal.ui.games.model.toUi
import myapplication.android.pixelpal.ui.ktx.asyncAwait
import myapplication.android.pixelpal.ui.ktx.runCatchingNonCancellation
import myapplication.android.pixelpal.ui.mvi.MviActor

class MainGamesActor(
    private val getGenresUseCase: GetGenresUseCase
): MviActor<
        MainGamesPartialState,
        MainGamesIntent,
        MainGamesState,
        MainGamesEffects>() {
    override fun resolve(
        intent: MainGamesIntent,
        state: MainGamesState
    ): Flow<MainGamesPartialState> =
        when(intent){
            MainGamesIntent.GetGenres -> loadGenres()
            MainGamesIntent.Init -> init()
        }

    private fun init() = flow { emit(MainGamesPartialState.Loading) }

    private fun loadGenres() =
        flow {
            kotlin.runCatching {
                getGenres()
            }.fold(
                onSuccess = { emit(MainGamesPartialState.DataLoaded(ui = it)) },
                onFailure = { emit(MainGamesPartialState.Error(throwable = it))}
            )
        }

    private suspend fun getGenres() =
        runCatchingNonCancellation {
            asyncAwait(
                {getGenresUseCase.invoke()}
            ){ genres ->
                genres.toUi()
            }
        }.getOrThrow()
}