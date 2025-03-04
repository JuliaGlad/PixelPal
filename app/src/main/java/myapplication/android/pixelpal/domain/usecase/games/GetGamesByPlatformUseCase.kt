package myapplication.android.pixelpal.domain.usecase.games

import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.domain.mapper.games.toDomain
import myapplication.android.pixelpal.domain.model.games.GamesMainInfoListDomain
import javax.inject.Inject

class GetGamesByPlatformUseCase @Inject constructor(
    private val gamesRepository: GamesRepository
) {
    suspend fun invoke(platformId: Long, page: Int): GamesMainInfoListDomain =
        gamesRepository.getGameByPlatform(platformId, page).toDomain()
}