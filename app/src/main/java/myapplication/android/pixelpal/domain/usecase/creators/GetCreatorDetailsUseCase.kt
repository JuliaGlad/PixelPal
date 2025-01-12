package myapplication.android.pixelpal.domain.usecase.creators

import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.domain.model.creator.CreatorDetailsDomain
import javax.inject.Inject

class GetCreatorDetailsUseCase @Inject constructor(
    val creatorsRepository: CreatorsRepository
) {
    suspend fun invoke(id: Long): CreatorDetailsDomain = creatorsRepository.getCreatorDetails(id)
}