package myapplication.android.pixelpal.data.repository.games

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import myapplication.android.pixelpal.data.repository.dto.game.GameDescriptionDto
import myapplication.android.pixelpal.data.repository.dto.game.GameNewsDtoList
import myapplication.android.pixelpal.data.repository.dto.game.GamesShortDtoList
import myapplication.android.pixelpal.data.repository.dto.game.ScreenshotDtoList
import myapplication.android.pixelpal.data.repository.mapper.game.toDto
import myapplication.android.pixelpal.data.source.games.GamesLocalSource
import myapplication.android.pixelpal.data.source.games.GamesRemoteSource
import myapplication.android.pixelpal.data.source.games.GamesShortDataLocalSource
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(
    private val localSourceGames: GamesLocalSource,
    private val remoteSourceGames: GamesRemoteSource,
    private val localSourceShortGames: GamesShortDataLocalSource,
) : GamesRepository {

    override suspend fun getGameScreenshots(
        gameId: String
    ): ScreenshotDtoList =
        withContext(Dispatchers.IO) { remoteSourceGames.getGameScreenshots(gameId).toDto() }

    override suspend fun getGamesFromTheSameSeries(
        gameId: String,
        page: Int
    ): GamesShortDtoList =
        withContext(Dispatchers.IO) {
            remoteSourceGames.getGamesFromTheSameSeries(gameId, page).toDto()
        }

    override suspend fun getGameAddition(
        gameId: String,
        page: Int
    ): GamesShortDtoList =
        withContext(Dispatchers.IO) {
            remoteSourceGames.getGameAddition(gameId, page).toDto()
        }

    override suspend fun getParentGames(
        gameId: String,
        page: Int
    ): GamesShortDtoList = withContext(Dispatchers.IO) {
        remoteSourceGames.getParentGames(gameId, page).toDto()
    }

    override suspend fun getGamesShortData(page: Int, genres: Long): GamesShortDtoList {
        val local = localSourceShortGames.getGamesShortData(page, genres)
        val result =
            if (local != null) local
            else {
                val remote = withContext(Dispatchers.IO) {
                   remoteSourceGames.getGamesShortData(page, genres)
                }
                localSourceShortGames.insertGamesShortData(page, remote, genres)
                remote
            }.toDto()
        return result
    }

    override suspend fun getTopGames(page: Int): GameNewsDtoList {
        val local = localSourceGames.getTopGames(page)
        val result =
            if (local != null) local
            else {
                val remote = withContext(Dispatchers.IO){
                    remoteSourceGames.getTopGames(page)
                }
                localSourceGames.insertTopGames(remote, page)
                remote
            }
        return result.toDto()
    }

    override suspend fun getGameNewReleases(dates: String, page: Int): GameNewsDtoList {
        val local = localSourceGames.getGameNewReleases(dates, page)
        return if (local != null) local
        else {
            val remote = withContext(Dispatchers.IO){
                remoteSourceGames.getGameByReleasesDate(dates, page)
            }
            localSourceGames.insertGameReleases(remote, page)
            remote
        }.toDto()
    }

    override suspend fun getGameMonthReleases(dates: String, page: Int): GameNewsDtoList {
        val local = localSourceGames.getGameMonthReleases(dates, page)
        return if (local != null) local
        else {
            val remote = withContext(Dispatchers.IO){
                remoteSourceGames.getGameByReleasesDate(dates, page)
            }
            localSourceGames.insertGameReleases(remote, page)
            remote
        }.toDto()
    }

    override suspend fun getGameByPlatform(platformId: Long, page: Int): GameNewsDtoList =
        withContext(Dispatchers.IO) {
            remoteSourceGames.getGameByPlatform(platformId, page).toDto()
        }

    override suspend fun getGameByStore(storeId: Int, page: Int): GameNewsDtoList =
        withContext(Dispatchers.IO){
            remoteSourceGames.getGameByStore(storeId, page).toDto()
        }

    override suspend fun getGameByPublisher(publisherId: Long, page: Int): GameNewsDtoList =
       withContext(Dispatchers.IO) {
           remoteSourceGames.getGameByPublisher(publisherId, page).toDto()
       }

    override suspend fun getGameByCreator(creatorId: Long, page: Int): GameNewsDtoList =
        withContext(Dispatchers.IO){
            remoteSourceGames.getGameByCreator(creatorId, page).toDto()
        }

    override suspend fun getGameDescription(gameId: Long): GameDescriptionDto =
       withContext(Dispatchers.IO){
           remoteSourceGames.getGameDescription(gameId).toDto()
       }

}