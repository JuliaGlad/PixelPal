package myapplication.android.pixelpal.domain.usecase.games

import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.domain.mapper.games.toDomain
import myapplication.android.pixelpal.domain.model.games.GamesMainInfoListDomain
import javax.inject.Inject

class GetGameByCreatorUseCase @Inject constructor(
    private val gamesRepository: GamesRepository
) {
    suspend fun invoke(creatorId: Long, page: Int): GamesMainInfoListDomain =
        gamesRepository.getGameByCreator(creatorId, page).toDomain()
}