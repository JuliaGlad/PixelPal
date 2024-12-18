package myapplication.android.pixelpal.data.source.games

import myapplication.android.pixelpal.data.models.gamesMain.GamesShortDataList
import myapplication.android.pixelpal.data.models.gamesNews.GamesNewsList

interface GamesRemoteSource {

    suspend fun getGamesShortData(id: Long): GamesShortDataList

    suspend fun getTopGames(): GamesNewsList

    suspend fun getGameByReleasesDate(date: String): GamesNewsList

}