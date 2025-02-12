package myapplication.android.pixelpal.domain.usecase.platofrms

import myapplication.android.pixelpal.data.repository.platforms.PlatformsRepository
import myapplication.android.pixelpal.domain.mapper.platforms.toDomain
import myapplication.android.pixelpal.domain.model.platform.PlatformDomainDetails
import javax.inject.Inject

class GetPlatformDetailsUseCase @Inject constructor(
    private val platformsRepository: PlatformsRepository
) {

    suspend fun invoke(id: Long): PlatformDomainDetails =
        platformsRepository.getPlatformDetails(id).toDomain()

}