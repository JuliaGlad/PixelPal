package myapplication.android.pixelpal.ui.home.mvi

import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.domain.usecase.games.GetGameMonthReleasesUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGamesNewReleasesUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetTopGamesUseCase
import javax.inject.Inject

class HomeLocalDI @Inject constructor(
    private val gamesRepository: GamesRepository
) {

    private val getTopGamesUseCase by lazy { GetTopGamesUseCase(gamesRepository) }

    private val getGamesNewReleasesUseCase by lazy { GetGamesNewReleasesUseCase(gamesRepository) }

    private val getGameMonthReleasesUseCase by lazy { GetGameMonthReleasesUseCase(gamesRepository) }

    val actor by lazy { HomeActor(getTopGamesUseCase, getGamesNewReleasesUseCase, getGameMonthReleasesUseCase) }

    val reducer by lazy { HomeReducer() }
}