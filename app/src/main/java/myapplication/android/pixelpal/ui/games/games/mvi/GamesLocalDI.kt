package myapplication.android.pixelpal.ui.games.games.mvi

import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.domain.usecase.games.GetGamesByQueryAndGenre
import myapplication.android.pixelpal.domain.usecase.games.GetGamesShortDataUseCase
import javax.inject.Inject

class GamesLocalDI @Inject constructor(
    private val gamesRepository: GamesRepository
) {

    private val getGamesByQueryAndGenreUseCase by lazy { GetGamesByQueryAndGenre(gamesRepository) }

    private val getGamesUseCase by lazy { GetGamesShortDataUseCase(gamesRepository) }

    val actor by lazy { GamesActor(getGamesUseCase, getGamesByQueryAndGenreUseCase) }

    val reducer by lazy { GamesReducer() }
}