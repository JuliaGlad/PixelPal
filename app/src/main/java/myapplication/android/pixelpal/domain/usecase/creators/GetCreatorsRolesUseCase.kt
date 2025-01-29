package myapplication.android.pixelpal.domain.usecase.creators

import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.domain.mapper.creators.toDomain
import myapplication.android.pixelpal.domain.model.creator.RoleDomain
import javax.inject.Inject

class GetCreatorsRolesUseCase @Inject constructor(
    private val creatorsRepository: CreatorsRepository
) {
    suspend fun invoke(): List<RoleDomain> = creatorsRepository.getCreatorsRoles().toDomain()
}