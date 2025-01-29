package myapplication.android.pixelpal.domain.usecase.creators

import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.domain.mapper.creators.toDomain
import javax.inject.Inject

class GetGameCreatorsUseCase @Inject constructor(
    val creatorsRepository: CreatorsRepository
) {

    suspend fun invoke(gameId: String, page: Int) =
        creatorsRepository.getGameCreators(gameId, page).toDomain()

}