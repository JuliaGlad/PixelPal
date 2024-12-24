package myapplication.android.pixelpal.domain.usecase.platofrms

import myapplication.android.pixelpal.data.repository.platforms.PlatformsRepository
import javax.inject.Inject

class GetPlatformsUseCase @Inject constructor(
    private val repository: PlatformsRepository
) {
    suspend fun invoke(page: Int) = repository.getPlatforms(page)
}