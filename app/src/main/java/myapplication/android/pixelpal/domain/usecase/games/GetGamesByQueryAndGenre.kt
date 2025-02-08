package myapplication.android.pixelpal.domain.usecase.games

import android.util.Log
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.domain.mapper.games.toDomain
import myapplication.android.pixelpal.domain.model.games.GamesShortDomainList
import javax.inject.Inject

class GetGamesByQueryAndGenre @Inject constructor(
    private val gamesRepository: GamesRepository
) {
    suspend fun invoke(page: Int, genre: Long, query: String): GamesShortDomainList {
        val games = gamesRepository.getGamesShortData(page, genre)
        val result = games.toDomain(query)
        Log.i("Games by query", result.games.size.toString())
        return result
    }

}