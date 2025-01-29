package myapplication.android.pixelpal.domain.usecase.games

import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.domain.mapper.games.toDomain
import myapplication.android.pixelpal.domain.model.games.GameDescriptionDomain
import javax.inject.Inject

class GetGameDescriptionUseCase @Inject constructor(
    val gamesRepository: GamesRepository
) {
    suspend fun invoke(gameId: Long): GameDescriptionDomain =
        gamesRepository.getGameDescription(gameId).toDomain()
}