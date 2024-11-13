package myapplication.android.pixelpal.domain.usecase.games

import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.domain.model.games.GamesNewsDomain

class GetTopGamesUseCase(
    private val repository: GamesRepository
) {
    suspend fun invoke(): List<GamesNewsDomain> = repository.getTopGames()
}