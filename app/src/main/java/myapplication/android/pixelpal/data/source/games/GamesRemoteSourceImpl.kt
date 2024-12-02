package myapplication.android.pixelpal.data.source.games

import myapplication.android.pixelpal.data.api.GamesApi
import myapplication.android.pixelpal.data.models.gamesMain.GamesShortDataList
import myapplication.android.pixelpal.data.models.gamesNews.GamesNewsList
import javax.inject.Inject

class GamesRemoteSourceImpl @Inject constructor(
    private val api: GamesApi,
): GamesRemoteSource {
    override suspend fun getGamesShortData(id: Long): GamesShortDataList =
        api.getGamesShortDataByGenre(id)

    override suspend fun getTopGames(): GamesNewsList =
        api.getTopGames()

    override suspend fun getGameByReleasesDate(date: String): GamesNewsList =
        api.getGamesByReleasesDate(date)

}