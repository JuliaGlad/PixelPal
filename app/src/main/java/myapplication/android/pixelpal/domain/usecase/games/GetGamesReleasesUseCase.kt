package myapplication.android.pixelpal.domain.usecase.games

import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.domain.model.games.GamesNewsDomain

class GetGamesReleasesUseCase(
    private val repository: GamesRepository
) {
    suspend fun invoke(date: String): List<GamesNewsDomain> = repository.getGameByReleasesDate(date)
}