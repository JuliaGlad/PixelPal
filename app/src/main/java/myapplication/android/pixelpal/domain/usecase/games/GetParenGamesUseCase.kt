package myapplication.android.pixelpal.domain.usecase.games

import myapplication.android.pixelpal.data.repository.games.GamesRepository
import javax.inject.Inject

class GetParenGamesUseCase @Inject constructor(
    val gamesRepository: GamesRepository
) {
    suspend fun invoke(gameId: String, page: Int) = gamesRepository.getParentGames(gameId, page)
}