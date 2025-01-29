package myapplication.android.pixelpal.domain.usecase.games

import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.domain.mapper.games.toDomain
import myapplication.android.pixelpal.domain.model.games.GamesNewsListDomain
import javax.inject.Inject

class GetGameByPublisherUseCase @Inject constructor(
    private val gamesRepository: GamesRepository
) {
    suspend fun invoke(publisherId: Long, page: Int): GamesNewsListDomain =
        gamesRepository.getGameByPublisher(publisherId, page).toDomain()
}