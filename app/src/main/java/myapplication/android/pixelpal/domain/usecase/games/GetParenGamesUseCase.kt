package myapplication.android.pixelpal.domain.usecase.games

import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.domain.mapper.games.toDomain
import myapplication.android.pixelpal.domain.model.games.GamesShortDomainList
import javax.inject.Inject

class GetParenGamesUseCase @Inject constructor(
    val gamesRepository: GamesRepository
) {
    suspend fun invoke(gameId: String, page: Int): GamesShortDomainList =
        gamesRepository.getParentGames(gameId, page).toDomain()
}