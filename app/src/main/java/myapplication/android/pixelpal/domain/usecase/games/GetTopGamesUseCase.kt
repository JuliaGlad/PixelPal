package myapplication.android.pixelpal.domain.usecase.games

import android.util.Log
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.domain.mapper.games.toDomain
import myapplication.android.pixelpal.domain.model.games.GamesNewsListDomain
import javax.inject.Inject

class GetTopGamesUseCase @Inject constructor(
    private val repository: GamesRepository
) {
    suspend fun invoke(page: Int): GamesNewsListDomain =
        repository.getTopGames(page).toDomain()
}