package myapplication.android.pixelpal.domain.usecase.creators

import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.domain.model.creator.CreatorDomainList
import javax.inject.Inject

class GetCreatorsUseCase @Inject constructor(
    private val creatorsRepository: CreatorsRepository
) {
    suspend fun invoke(page: Int, roleId: Int): CreatorDomainList =
        creatorsRepository.getCreators(page, roleId)
}