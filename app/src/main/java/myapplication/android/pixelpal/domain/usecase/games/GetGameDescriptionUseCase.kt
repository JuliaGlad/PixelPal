package myapplication.android.pixelpal.domain.usecase.games

import myapplication.android.pixelpal.data.repository.games.GamesRepository
import javax.inject.Inject

class GetGameDescriptionUseCase @Inject constructor(
    val gamesRepository: GamesRepository
) {
    suspend fun invoke(gameId: Long) = gamesRepository.getGameDescription(gameId)
}