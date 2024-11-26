package myapplication.android.pixelpal.domain.usecase.games

import myapplication.android.pixelpal.data.repository.games.GamesRepository

class GetGamesShortDataUseCase(
    private val gamesRepository: GamesRepository
) {
    suspend fun invoke(genre: Long) = gamesRepository.getGamesShortData(genre)
}