package myapplication.android.pixelpal.domain.usecase.favorite_games

import myapplication.android.pixelpal.data.repository.favorite_games.FavoriteGamesRepository
import myapplication.android.pixelpal.domain.mapper.games.toDomain
import myapplication.android.pixelpal.domain.model.games.GamesMainInfoListDomain
import javax.inject.Inject

class GetFavoriteGamesUseCase @Inject constructor(
    private val favoriteGamesRepository: FavoriteGamesRepository
) {
    suspend fun invoke(): GamesMainInfoListDomain = favoriteGamesRepository.getFavoriteGames().toDomain()
}