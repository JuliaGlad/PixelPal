package myapplication.android.pixelpal.domain.usecase.creators

import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.domain.model.creator.CreatorDomainList

class GetCreatorsUseCase(
    private val creatorsRepository: CreatorsRepository
) {
    suspend fun invoke(roleId: Int): CreatorDomainList = creatorsRepository.getCreators(roleId)
}