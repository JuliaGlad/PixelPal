package myapplication.android.pixelpal.data.source.games

import myapplication.android.pixelpal.data.api.GamesApi
import myapplication.android.pixelpal.data.models.gamesMain.GamesShortDataList
import myapplication.android.pixelpal.data.models.gamesNews.GamesNewsList

public class GamesRemoteSource(
    private val api: GamesApi,
) {
    suspend fun getGamesShortData(): GamesShortDataList =
        api.getGamesShortData()

    suspend fun getTopGames(): GamesNewsList =
        api.getTopGames()

    suspend fun getGameByReleasesDate(date: String): GamesNewsList =
        api.getGamesByReleasesDate(date)

}