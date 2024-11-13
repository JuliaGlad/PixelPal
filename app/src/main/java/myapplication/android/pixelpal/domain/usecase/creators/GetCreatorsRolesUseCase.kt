package myapplication.android.pixelpal.domain.usecase.creators

import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.domain.model.creator.CreatorRoleDomain

class GetCreatorsRolesUseCase(
    private val creatorsRepository: CreatorsRepository
) {
    suspend fun invoke(): List<CreatorRoleDomain> = creatorsRepository.getCreatorsRoles()
}