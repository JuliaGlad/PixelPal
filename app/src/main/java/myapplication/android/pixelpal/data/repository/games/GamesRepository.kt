package myapplication.android.pixelpal.data.repository.games

import myapplication.android.pixelpal.data.models.game_description.GameDescription
import myapplication.android.pixelpal.data.models.gamesMain.GamesShortDataList
import myapplication.android.pixelpal.data.models.screenshots.ScreenshotsList
import myapplication.android.pixelpal.domain.model.games.GameDescriptionDomain
import myapplication.android.pixelpal.domain.model.games.GamesNewsListDomain
import myapplication.android.pixelpal.domain.model.games.GamesShortDomainList
import myapplication.android.pixelpal.domain.model.screenshot.ScreenShotDomainList

interface GamesRepository {
    suspend fun getGameByPlatform(platformId: Int, page: Int): GamesNewsListDomain

    suspend fun getGameByStore(storeId: Int, page: Int): GamesNewsListDomain

    suspend fun getGameByPublisher(publisherId: Long, page: Int): GamesNewsListDomain

    suspend fun getGameByCreator(creatorId: Long, page: Int): GamesNewsListDomain

    suspend fun getGameDescription(gameId: Long): GameDescriptionDomain

    suspend fun getGameScreenshots(gameId: String): ScreenShotDomainList

    suspend fun getGamesFromTheSameSeries(gameId: String, page: Int): GamesShortDomainList

    suspend fun getGameAddition(gameId: String, page: Int): GamesShortDomainList

    suspend fun getParentGames(gameId: String, page: Int): GamesShortDomainList

    suspend fun getGamesShortData(page: Int, genres: Long) : GamesShortDomainList

    suspend fun getTopGames(page: Int): GamesNewsListDomain

    suspend fun getGameNewReleases(dates: String, page: Int): GamesNewsListDomain

    suspend fun getGameMonthReleases(dates: String, page: Int): GamesNewsListDomain
}