package myapplication.android.pixelpal.ui.all_games.mvi

import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.domain.usecase.games.GetGameMonthReleasesUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGamesNewReleasesUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetTopGamesUseCase
import javax.inject.Inject

class AllGamesLocalDI @Inject constructor(
    val gamesRepository: GamesRepository
) {
    private val getTopGamesUseCase by lazy { GetTopGamesUseCase(gamesRepository) }

    private val getGamesNewReleasesUseCase by lazy { GetGamesNewReleasesUseCase(gamesRepository) }

    private val getGameMonthReleasesUseCase by lazy { GetGameMonthReleasesUseCase(gamesRepository) }

    val actor by lazy { AllGamesActor(getTopGamesUseCase, getGamesNewReleasesUseCase, getGameMonthReleasesUseCase) }

    val reducer by lazy { AllGamesReducer() }

}