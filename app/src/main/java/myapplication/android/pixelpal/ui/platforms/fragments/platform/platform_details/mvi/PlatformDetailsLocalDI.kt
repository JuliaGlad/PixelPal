package myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.mvi

import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.data.repository.platforms.PlatformsRepository
import myapplication.android.pixelpal.domain.usecase.games.GetGamesByPlatformUseCase
import myapplication.android.pixelpal.domain.usecase.platofrms.GetPlatformDetailsUseCase
import javax.inject.Inject

class PlatformDetailsLocalDI @Inject constructor(
    val platformsRepository: PlatformsRepository,
    val gamesRepository: GamesRepository
) {
    private val getGameByPlatformDetailsUseCase by lazy { GetGamesByPlatformUseCase(gamesRepository) }

    private val getPlatformDetailsUseCase by lazy { GetPlatformDetailsUseCase(platformsRepository) }

    val actor by lazy { PlatformDetailsActor(getPlatformDetailsUseCase, getGameByPlatformDetailsUseCase) }

    val reducer by lazy { PlatformDetailsReducer() }
}