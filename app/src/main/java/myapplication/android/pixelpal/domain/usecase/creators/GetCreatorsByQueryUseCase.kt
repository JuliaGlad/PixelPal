package myapplication.android.pixelpal.domain.usecase.creators

import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.domain.mapper.creators.toDomainWithQuery
import myapplication.android.pixelpal.domain.model.creator.CreatorDomainList
import javax.inject.Inject

class GetCreatorsByQueryUseCase @Inject constructor(
    private val creatorsRepository: CreatorsRepository
) {
    suspend fun invoke(page: Int, roleId: Int, query: String): CreatorDomainList =
        creatorsRepository.getCreators(page, roleId).toDomainWithQuery(roleId, query)
}