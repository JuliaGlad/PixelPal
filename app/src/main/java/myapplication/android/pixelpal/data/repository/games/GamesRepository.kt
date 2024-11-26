package myapplication.android.pixelpal.data.repository.games

import myapplication.android.pixelpal.data.models.gamesMain.GamesShortDataList
import myapplication.android.pixelpal.data.models.gamesNews.GamesNewsList
import myapplication.android.pixelpal.domain.model.games.GamesNewsListDomain
import myapplication.android.pixelpal.domain.model.games.GamesShortDomainList

interface GamesRepository {

    suspend fun getGamesShortData(genres: Long) : GamesShortDomainList

    suspend fun getTopGames(): GamesNewsListDomain

    suspend fun getGameByReleasesDate(date: String): GamesNewsListDomain

    fun getLocalTopGames() : GamesNewsList?

    fun getLocalGamesShortData() : GamesShortDataList?

    fun getLocalGamesByReleaseDate(date: String) : GamesNewsList?

}