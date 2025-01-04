package myapplication.android.pixelpal.ui.game_details.mvi

import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.data.repository.stores.StoresRepository
import myapplication.android.pixelpal.domain.usecase.creators.GetGameCreatorsUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGameAdditionsUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGameDescriptionUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGameScreenshotsUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGamesFromSameSeriesUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetParenGamesUseCase
import myapplication.android.pixelpal.domain.usecase.stores.GetStoresSellingGameUseCase
import javax.inject.Inject

class GameDetailsLocalDI@Inject constructor(
    private val creatorsRepository: CreatorsRepository,
    private val gamesRepository: GamesRepository,
    private val storesRepository: StoresRepository
) {
    private val getStoresSellingGameUseCase by lazy { GetStoresSellingGameUseCase(storesRepository) }

    private val getGameCreatorsUseCase by lazy { GetGameCreatorsUseCase(creatorsRepository) }

    private val getParenGamesUseCase by lazy { GetParenGamesUseCase(gamesRepository) }

    private val getGameAdditionsUseCase by lazy { GetGameAdditionsUseCase(gamesRepository) }

    private val getGamesFromSameSeriesUseCase by lazy { GetGamesFromSameSeriesUseCase(gamesRepository) }

    private val getGameDescriptionUseCase by lazy { GetGameDescriptionUseCase(gamesRepository) }

    private val getGameScreenshotsUseCase by lazy { GetGameScreenshotsUseCase(gamesRepository) }

    val actor by lazy { GameDetailsActor(
        getStoresSellingGameUseCase,
        getGameCreatorsUseCase,
        getGameAdditionsUseCase,
        getGameDescriptionUseCase,
        getGameScreenshotsUseCase,
        getParenGamesUseCase,
        getGamesFromSameSeriesUseCase
    )}

    val reducer by lazy { GameDetailsReducer() }

}