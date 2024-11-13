package myapplication.android.pixelpal.domain.usecase.platofrms

import myapplication.android.pixelpal.data.repository.platforms.PlatformsRepository

class GetPlatformsUseCase(
    private val repository: PlatformsRepository
) {
    suspend fun invoke() = repository.getPlatforms()
}