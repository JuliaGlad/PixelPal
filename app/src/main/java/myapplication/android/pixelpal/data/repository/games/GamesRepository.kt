package myapplication.android.pixelpal.data.repository.games

import myapplication.android.pixelpal.data.repository.dto.game.GameDescriptionDto
import myapplication.android.pixelpal.data.repository.dto.game.GameNewsDtoList
import myapplication.android.pixelpal.data.repository.dto.game.GamesShortDtoList
import myapplication.android.pixelpal.data.repository.dto.game.ScreenshotDtoList

interface GamesRepository {
    suspend fun getGameByPlatform(platformId: Long, page: Int): GameNewsDtoList

    suspend fun getGameByStore(storeId: Int, page: Int): GameNewsDtoList

    suspend fun getGameByPublisher(publisherId: Long, page: Int): GameNewsDtoList

    suspend fun getGameByCreator(creatorId: Long, page: Int): GameNewsDtoList

    suspend fun getGameDescription(gameId: Long): GameDescriptionDto

    suspend fun getGameScreenshots(gameId: String): ScreenshotDtoList

    suspend fun getGamesFromTheSameSeries(gameId: String, page: Int): GamesShortDtoList

    suspend fun getGameAddition(gameId: String, page: Int): GamesShortDtoList

    suspend fun getParentGames(gameId: String, page: Int): GamesShortDtoList

    suspend fun getGamesShortData(page: Int, genres: Long) : GamesShortDtoList

    suspend fun getTopGames(page: Int): GameNewsDtoList

    suspend fun getGameNewReleases(dates: String, page: Int): GameNewsDtoList

    suspend fun getGameMonthReleases(dates: String, page: Int): GameNewsDtoList
}