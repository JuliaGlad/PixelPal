package myapplication.android.pixelpal.domain.usecase.favorite_games

import myapplication.android.pixelpal.data.repository.favorite_games.FavoriteGamesRepository
import javax.inject.Inject

class AddFavoriteGamesUseCase @Inject constructor(
    private val favoriteGamesRepository: FavoriteGamesRepository
) {
    suspend fun invoke(gameId: Long) = favoriteGamesRepository.addGame(gameId)
}