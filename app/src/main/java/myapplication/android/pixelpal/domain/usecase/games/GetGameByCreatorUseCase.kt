package myapplication.android.pixelpal.domain.usecase.games

import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.domain.mapper.games.toDomain
import myapplication.android.pixelpal.domain.model.games.GamesNewsListDomain
import myapplication.android.pixelpal.domain.model.games.GamesShortDomainList
import javax.inject.Inject

class GetGameByCreatorUseCase @Inject constructor(
    private val gamesRepository: GamesRepository
) {
    suspend fun invoke(creatorId: Long, page: Int): GamesNewsListDomain =
        gamesRepository.getGameByCreator(creatorId, page).toDomain()
}