package myapplication.android.pixelpal.domain.usecase.games

import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.domain.model.games.GamesNewsListDomain

class GetTopGamesUseCase(
    private val repository: GamesRepository
) {
    suspend fun invoke(): GamesNewsListDomain = repository.getTopGames()
}