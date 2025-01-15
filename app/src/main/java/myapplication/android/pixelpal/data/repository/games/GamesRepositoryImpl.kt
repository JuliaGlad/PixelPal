package myapplication.android.pixelpal.data.repository.games

import android.util.Log
import myapplication.android.pixelpal.data.models.game_description.GameDescription
import myapplication.android.pixelpal.data.repository.getAndCheckData
import myapplication.android.pixelpal.data.source.games.GamesLocalSource
import myapplication.android.pixelpal.data.source.games.GamesRemoteSource
import myapplication.android.pixelpal.data.source.games.GamesShortDataLocalSource
import myapplication.android.pixelpal.domain.model.games.GameDescriptionDomain
import myapplication.android.pixelpal.domain.model.games.GamesNewsListDomain
import myapplication.android.pixelpal.domain.model.games.GamesShortDomainList
import myapplication.android.pixelpal.domain.model.screenshot.ScreenShotDomainList
import myapplication.android.pixelpal.domain.wrapper.games.toDomain
import myapplication.android.pixelpal.domain.wrapper.screenshots.toDomain
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(
    private val localSourceGames: GamesLocalSource,
    private val remoteSourceGames: GamesRemoteSource,
    private val localSourceShortGames: GamesShortDataLocalSource,
) : GamesRepository {

    override suspend fun getGameScreenshots(
        gameId: String
    ): ScreenShotDomainList = remoteSourceGames.getGameScreenshots(gameId).toDomain()

    override suspend fun getGamesFromTheSameSeries(
        gameId: String,
        page: Int
    ): GamesShortDomainList = remoteSourceGames.getGamesFromTheSameSeries(gameId, page).toDomain()

    override suspend fun getGameAddition(
        gameId: String,
        page: Int
    ): GamesShortDomainList = remoteSourceGames.getGameAddition(gameId, page).toDomain()

    override suspend fun getParentGames(
        gameId: String,
        page: Int
    ): GamesShortDomainList = remoteSourceGames.getParentGames(gameId, page).toDomain()

    override suspend fun getGamesShortData(page: Int, genres: Long): GamesShortDomainList {
        val local = localSourceShortGames.getGamesShortData(page, genres)
        val result =
            if (local != null) local
            else {
                val remote = remoteSourceGames.getGamesShortData(page, genres)
                localSourceShortGames.insertGamesShortData(page, remote, genres)
                remote
            }.toDomain()
        return result
    }

    override suspend fun getTopGames(page: Int): GamesNewsListDomain {
        val local = localSourceGames.getTopGames(page)
        val result =
            if (local != null) local
            else {
                val remote = remoteSourceGames.getTopGames(page)
                localSourceGames.insertTopGames(remote, page)
                remote
            }
        return result.toDomain()
    }

    override suspend fun getGameNewReleases(dates: String, page: Int): GamesNewsListDomain {
        val local = localSourceGames.getGameNewReleases(dates, page)
        return if (local != null) local
        else {
            val remote = remoteSourceGames.getGameByReleasesDate(dates, page)
            localSourceGames.insertGameReleases(remote, page)
            remote
        }.toDomain()
    }

    override suspend fun getGameMonthReleases(dates: String, page: Int): GamesNewsListDomain {
        val local = localSourceGames.getGameMonthReleases(dates, page)
        return if (local != null) local
        else {
            val remote = remoteSourceGames.getGameByReleasesDate(dates, page)
            localSourceGames.insertGameReleases(remote, page)
            remote
        }.toDomain()
    }

    override suspend fun getGameByPlatform(platformId: Int, page: Int): GamesNewsListDomain =
        remoteSourceGames.getGameByPlatform(platformId, page).toDomain()

    override suspend fun getGameByStore(storeId: Int, page: Int): GamesNewsListDomain =
        remoteSourceGames.getGameByStore(storeId, page).toDomain()

    override suspend fun getGameByPublisher(publisherId: Long, page: Int): GamesNewsListDomain =
        remoteSourceGames.getGameByPublisher(publisherId, page).toDomain()

    override suspend fun getGameByCreator(creatorId: Long, page: Int): GamesNewsListDomain =
        remoteSourceGames.getGameByCreator(creatorId, page).toDomain()

    override suspend fun getGameDescription(gameId: Long): GameDescriptionDomain =
        remoteSourceGames.getGameDescription(gameId).toDomain()

}