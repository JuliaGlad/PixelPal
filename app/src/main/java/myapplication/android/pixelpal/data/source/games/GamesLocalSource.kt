package myapplication.android.pixelpal.data.source.games

import myapplication.android.pixelpal.data.models.gamesMain.GamesShortDataList
import myapplication.android.pixelpal.data.models.gamesNews.GamesNewsList

interface GamesLocalSource {

    suspend fun getGamesShortData(id: Long): GamesShortDataList?

    suspend fun getTopGames(): GamesNewsList?

    suspend fun getGameByReleasesDate(date: String): GamesNewsList?

}