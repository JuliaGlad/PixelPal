package myapplication.android.pixelpal.data.source.games

import myapplication.android.pixelpal.data.models.game_description.GameDescription
import myapplication.android.pixelpal.data.models.gamesMain.GameShortData
import myapplication.android.pixelpal.data.models.gamesMain.GamesShortDataList
import myapplication.android.pixelpal.data.models.gamesNews.GamesMainInfo
import myapplication.android.pixelpal.data.models.gamesNews.GamesMainInfoList
import myapplication.android.pixelpal.data.models.screenshots.ScreenshotsList

interface GamesRemoteSource {

    suspend fun getGameById(gameId: Long): GamesMainInfo

    suspend fun getGameByPlatform(platformId: Long, page: Int): GamesMainInfoList

    suspend fun getGameByStore(storeId: Int, page: Int): GamesMainInfoList

    suspend fun getGameByPublisher(publisherId: Long, page: Int): GamesMainInfoList

    suspend fun getGameByCreator(creatorId: Long, page: Int): GamesMainInfoList

    suspend fun getGameDescription(gameId: Long): GameDescription

    suspend fun getGameScreenshots(gameId: String): ScreenshotsList

    suspend fun getGamesFromTheSameSeries(gameId: String, page: Int): GamesShortDataList

    suspend fun getGameAddition(gameId: String, page: Int): GamesShortDataList

    suspend fun getParentGames(gameId: String, page: Int): GamesShortDataList

    suspend fun getGamesShortData(page: Int, id: Long): GamesShortDataList

    suspend fun getTopGames(page: Int): GamesMainInfoList

    suspend fun getGameByReleasesDate(date: String, page: Int): GamesMainInfoList

}