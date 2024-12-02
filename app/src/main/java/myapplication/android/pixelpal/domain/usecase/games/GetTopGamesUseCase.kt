package myapplication.android.pixelpal.domain.usecase.games

import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.domain.model.games.GamesNewsListDomain
import javax.inject.Inject

class GetTopGamesUseCase @Inject constructor(
    private val repository: GamesRepository
) {
    suspend fun invoke(): GamesNewsListDomain = repository.getTopGames()
}