package myapplication.android.pixelpal.domain.usecase.favorite_games

import myapplication.android.pixelpal.data.repository.favorite_games.FavoriteGamesRepository
import javax.inject.Inject

class RemoveFavoriteGameUseCase @Inject constructor(
    private val favoriteGamesRepository: FavoriteGamesRepository
) {
    suspend fun invoke(gameId: Long) = favoriteGamesRepository.removeGame(gameId)
}