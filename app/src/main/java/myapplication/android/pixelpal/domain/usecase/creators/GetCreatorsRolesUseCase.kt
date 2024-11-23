package myapplication.android.pixelpal.domain.usecase.creators

import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.domain.model.creator.RoleDomain

class GetCreatorsRolesUseCase(
    private val creatorsRepository: CreatorsRepository
) {
    suspend fun invoke(): List<RoleDomain> = creatorsRepository.getCreatorsRoles()
}