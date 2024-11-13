package myapplication.android.pixelpal.data.repository.games

import myapplication.android.pixelpal.data.models.gamesNews.GamesNewsList
import myapplication.android.pixelpal.domain.model.games.GamesNewsDomain
import myapplication.android.pixelpal.domain.model.games.GamesShortDomain

interface GamesRepository {

    suspend fun getGamesShortData() : List<GamesShortDomain>

    suspend fun getTopGames(): List<GamesNewsDomain>

    suspend fun getGameByReleasesDate(date: String): List<GamesNewsDomain>

    fun getLocalTopGames() : GamesNewsList?

    fun getLocalGamesShortData() : GamesNewsList?

    fun getLocalGamesByReleaseDate(date: String) : GamesNewsList?

}