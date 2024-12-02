package myapplication.android.pixelpal.ui.home.mvi

import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.domain.usecase.games.GetGamesReleasesUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetTopGamesUseCase
import javax.inject.Inject

class HomeLocalDI @Inject constructor(
    private val gamesRepository: GamesRepository
) {

    private val getTopGamesUseCase by lazy { GetTopGamesUseCase(gamesRepository) }

    private val getGamesReleasesUseCase by lazy { GetGamesReleasesUseCase(gamesRepository) }

    val actor by lazy { HomeActor(getTopGamesUseCase, getGamesReleasesUseCase) }

    val reducer by lazy { HomeReducer() }
}