package myapplication.android.pixelpal.data.source.games

import myapplication.android.pixelpal.data.models.game_description.GameDescription
import myapplication.android.pixelpal.data.models.gamesMain.GamesShortDataList
import myapplication.android.pixelpal.data.models.gamesNews.GamesNewsList
import myapplication.android.pixelpal.data.models.screenshots.ScreenshotsList
import myapplication.android.pixelpal.domain.model.games.GamesNewsListDomain

interface GamesRemoteSource {
    suspend fun getGameByPlatform(platformId: Long, page: Int): GamesNewsList

    suspend fun getGameByStore(storeId: Int, page: Int): GamesNewsList

    suspend fun getGameByPublisher(publisherId: Long, page: Int): GamesNewsList

    suspend fun getGameByCreator(creatorId: Long, page: Int): GamesNewsList

    suspend fun getGameDescription(gameId: Long): GameDescription

    suspend fun getGameScreenshots(gameId: String): ScreenshotsList

    suspend fun getGamesFromTheSameSeries(gameId: String, page: Int): GamesShortDataList

    suspend fun getGameAddition(gameId: String, page: Int): GamesShortDataList

    suspend fun getParentGames(gameId: String, page: Int): GamesShortDataList

    suspend fun getGamesShortData(page: Int, id: Long): GamesShortDataList

    suspend fun getTopGames(page: Int): GamesNewsList

    suspend fun getGameByReleasesDate(date: String, page: Int): GamesNewsList

}