package myapplication.android.pixelpal.data.source.games

import myapplication.android.pixelpal.data.api.GamesApi
import myapplication.android.pixelpal.data.models.game_description.GameDescription
import myapplication.android.pixelpal.data.models.gamesMain.GamesShortDataList
import myapplication.android.pixelpal.data.models.gamesNews.GamesNewsList
import myapplication.android.pixelpal.data.models.screenshots.ScreenshotsList
import javax.inject.Inject

class GamesRemoteSourceImpl @Inject constructor(
    private val api: GamesApi,
): GamesRemoteSource {
    override suspend fun getGameDescription(gameId: Long): GameDescription =
        api.getGameDescription(gameId)

    override suspend fun getGameScreenshots(gameId: String): ScreenshotsList =
        api.getGameScreenshots(gameId)

    override suspend fun getGamesFromTheSameSeries(gameId: String, page: Int): GamesShortDataList =
        api.getGamesFromSameSeries(gameId, page)

    override suspend fun getGameAddition(gameId: String, page: Int): GamesShortDataList =
        api.getGameAdditions(gameId, page)

    override suspend fun getParentGames(gameId: String, page: Int): GamesShortDataList =
        api.getParentGames(gameId, page)

    override suspend fun getGamesShortData(page: Int, id: Long): GamesShortDataList =
        api.getGamesShortDataByGenre(page, id)

    override suspend fun getTopGames(page: Int): GamesNewsList =
        api.getTopGames(page)

    override suspend fun getGameByReleasesDate(date: String, page: Int): GamesNewsList =
        api.getGamesByReleasesDate(date, page)

}