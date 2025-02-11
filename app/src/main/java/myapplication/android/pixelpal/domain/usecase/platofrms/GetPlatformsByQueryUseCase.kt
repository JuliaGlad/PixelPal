package myapplication.android.pixelpal.domain.usecase.platofrms

import myapplication.android.pixelpal.data.repository.platforms.PlatformsRepository
import myapplication.android.pixelpal.domain.mapper.platforms.toDomain
import javax.inject.Inject

class GetPlatformsByQueryUseCase @Inject constructor(
    private val platformsRepository: PlatformsRepository
){
    suspend fun invoke(page: Int, query: String) =
        platformsRepository.getPlatforms(page).toDomain(query)
}