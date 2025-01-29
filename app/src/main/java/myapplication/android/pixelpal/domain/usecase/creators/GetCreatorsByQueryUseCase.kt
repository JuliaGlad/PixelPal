package myapplication.android.pixelpal.domain.usecase.creators

import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import javax.inject.Inject

class GetCreatorsByQueryUseCase @Inject constructor(
    private val creatorsRepository: CreatorsRepository
) {
//    suspend fun invoke(page: Int, roleId: Int, query: String) =
//        creatorsRepository.getCreators(page, roleId)
}