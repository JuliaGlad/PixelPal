package myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi

import myapplication.android.pixelpal.data.repository.platforms.PlatformsRepository
import myapplication.android.pixelpal.domain.usecase.platofrms.GetPlatformsByQueryUseCase
import myapplication.android.pixelpal.domain.usecase.platofrms.GetPlatformsUseCase
import javax.inject.Inject

class PlatformLocalDI @Inject constructor(
    private val platformsRepository: PlatformsRepository
) {

    private val getPlatformsByQueryUseCase by lazy { GetPlatformsByQueryUseCase(platformsRepository) }

    private val getPlatformsUseCase by lazy { GetPlatformsUseCase(platformsRepository) }

    val actor by lazy { PlatformActor(getPlatformsUseCase, getPlatformsByQueryUseCase) }

    val reducer by lazy { PlatformReducer() }
}