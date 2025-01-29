package myapplication.android.pixelpal.domain.usecase.platofrms

import myapplication.android.pixelpal.data.repository.platforms.PlatformsRepository
import myapplication.android.pixelpal.domain.mapper.platforms.toDomain
import myapplication.android.pixelpal.domain.model.platform.PlatformDomainList
import javax.inject.Inject

class GetPlatformsUseCase @Inject constructor(
    private val repository: PlatformsRepository
) {
    suspend fun invoke(page: Int): PlatformDomainList =
        repository.getPlatforms(page).toDomain()
}