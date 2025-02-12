package myapplication.android.pixelpal.ui.all_games.mvi

import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.domain.usecase.games.GetGameAdditionsUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGameByCreatorUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGameByPublisherUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGameMonthReleasesUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGamesByPlatformUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGamesByStoreUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGamesFromSameSeriesUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGamesNewReleasesUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetParenGamesUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetTopGamesUseCase
import javax.inject.Inject

class AllGamesLocalDI @Inject constructor(
    val gamesRepository: GamesRepository
) {
    private val getTopGamesUseCase by lazy { GetTopGamesUseCase(gamesRepository) }

    private val getGamesNewReleasesUseCase by lazy { GetGamesNewReleasesUseCase(gamesRepository) }

    private val getGameMonthReleasesUseCase by lazy { GetGameMonthReleasesUseCase(gamesRepository) }

    private val getGamesFromSameSeriesUseCase by lazy { GetGamesFromSameSeriesUseCase(gamesRepository) }

    private val getGameAdditionsUseCase by lazy { GetGameAdditionsUseCase(gamesRepository) }

    private val getGameByCreatorUseCase by lazy { GetGameByCreatorUseCase(gamesRepository) }

    private val getGamesByPlatformUseCase by lazy { GetGamesByPlatformUseCase(gamesRepository) }

    private val getParenGamesUseCase by lazy { GetParenGamesUseCase(gamesRepository) }

    private val getGamesByStoreUseCase by lazy { GetGamesByStoreUseCase(gamesRepository) }

    private val getGamesByPublisherUseCase by lazy { GetGameByPublisherUseCase(gamesRepository) }

    val actor by lazy { AllGamesActor(
        getTopGamesUseCase,
        getGamesNewReleasesUseCase,
        getGameMonthReleasesUseCase,
        getParenGamesUseCase,
        getGameAdditionsUseCase,
        getGamesFromSameSeriesUseCase,
        getGameByCreatorUseCase,
        getGamesByPlatformUseCase,
        getGamesByStoreUseCase,
        getGamesByPublisherUseCase
    ) }

    val reducer by lazy { AllGamesReducer() }

}