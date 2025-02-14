package myapplication.android.pixelpal.data.repository.games

import myapplication.android.pixelpal.data.repository.dto.game.GameDetailsDto
import myapplication.android.pixelpal.data.repository.dto.game.GameMainInfoDtoList
import myapplication.android.pixelpal.data.repository.dto.game.GamesShortDtoList
import myapplication.android.pixelpal.data.repository.dto.game.ScreenshotDtoList

interface GamesRepository {
    suspend fun getGameByPlatform(platformId: Long, page: Int): GameMainInfoDtoList

    suspend fun getGameByStore(storeId: Int, page: Int): GameMainInfoDtoList

    suspend fun getGameByPublisher(publisherId: Long, page: Int): GameMainInfoDtoList

    suspend fun getGameByCreator(creatorId: Long, page: Int): GameMainInfoDtoList

    suspend fun getGameDescription(gameId: Long): GameDetailsDto

    suspend fun getGameScreenshots(gameId: String): ScreenshotDtoList

    suspend fun getGamesFromTheSameSeries(gameId: String, page: Int): GamesShortDtoList

    suspend fun getGameAddition(gameId: String, page: Int): GamesShortDtoList

    suspend fun getParentGames(gameId: String, page: Int): GamesShortDtoList

    suspend fun getGamesShortData(page: Int, genres: Long) : GamesShortDtoList

    suspend fun getTopGames(page: Int): GameMainInfoDtoList

    suspend fun getGameNewReleases(dates: String, page: Int): GameMainInfoDtoList

    suspend fun getGameMonthReleases(dates: String, page: Int): GameMainInfoDtoList
}