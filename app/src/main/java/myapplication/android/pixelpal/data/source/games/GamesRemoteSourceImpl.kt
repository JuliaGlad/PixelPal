package myapplication.android.pixelpal.data.source.games

import myapplication.android.pixelpal.data.api.GamesApi
import myapplication.android.pixelpal.data.models.gamesMain.GamesShortDataList
import myapplication.android.pixelpal.data.models.gamesNews.GamesNewsList
import javax.inject.Inject

class GamesRemoteSourceImpl @Inject constructor(
    private val api: GamesApi,
): GamesRemoteSource {
    override suspend fun getGamesShortData(page: Int, id: Long): GamesShortDataList =
        api.getGamesShortDataByGenre(page, id)

    override suspend fun getTopGames(page: Int): GamesNewsList =
        api.getTopGames(page)

    override suspend fun getGameByReleasesDate(date: String, page: Int): GamesNewsList =
        api.getGamesByReleasesDate(date, page)

}