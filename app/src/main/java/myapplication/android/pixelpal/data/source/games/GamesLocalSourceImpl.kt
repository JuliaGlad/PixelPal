package myapplication.android.pixelpal.data.source.games

import myapplication.android.pixelpal.data.models.gamesMain.GamesShortDataList
import myapplication.android.pixelpal.data.models.gamesNews.GamesNewsList
import javax.inject.Inject

class GamesLocalSourceImpl @Inject constructor(): GamesLocalSource {
    override suspend fun getGamesShortData(id: Long): GamesShortDataList? = null

    override suspend fun getTopGames(): GamesNewsList? = null

    override suspend fun getGameByReleasesDate(date: String): GamesNewsList? = null
}