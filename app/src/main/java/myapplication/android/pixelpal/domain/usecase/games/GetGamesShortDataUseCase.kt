package myapplication.android.pixelpal.domain.usecase.games

import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.domain.mapper.games.toDomain
import myapplication.android.pixelpal.domain.model.games.GamesShortDomainList
import javax.inject.Inject

class GetGamesShortDataUseCase @Inject constructor(
    private val gamesRepository: GamesRepository
) {
    suspend fun invoke(page: Int, genre: Long): GamesShortDomainList =
        gamesRepository.getGamesShortData(page, genre).toDomain()
}