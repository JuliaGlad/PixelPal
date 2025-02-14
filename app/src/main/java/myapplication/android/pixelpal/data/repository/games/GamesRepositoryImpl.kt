package myapplication.android.pixelpal.data.repository.games

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import myapplication.android.pixelpal.app.Constants
import myapplication.android.pixelpal.data.repository.dto.game.GameDetailsDto
import myapplication.android.pixelpal.data.repository.dto.game.GameMainInfoDtoList
import myapplication.android.pixelpal.data.repository.dto.game.GamesShortDtoList
import myapplication.android.pixelpal.data.repository.dto.game.ScreenshotDtoList
import myapplication.android.pixelpal.data.repository.mapper.game.toDto
import myapplication.android.pixelpal.data.repository.user.FirebaseService
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

    override suspend fun getTopGames(page: Int): GameMainInfoDtoList {
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

    override suspend fun getGameNewReleases(dates: String, page: Int): GameMainInfoDtoList {
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

    override suspend fun getGameMonthReleases(dates: String, page: Int): GameMainInfoDtoList {
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

    override suspend fun getGameByPlatform(platformId: Long, page: Int): GameMainInfoDtoList =
        withContext(Dispatchers.IO) {
            remoteSourceGames.getGameByPlatform(platformId, page).toDto()
        }

    override suspend fun getGameByStore(storeId: Int, page: Int): GameMainInfoDtoList =
        withContext(Dispatchers.IO){
            remoteSourceGames.getGameByStore(storeId, page).toDto()
        }

    override suspend fun getGameByPublisher(publisherId: Long, page: Int): GameMainInfoDtoList =
       withContext(Dispatchers.IO) {
           remoteSourceGames.getGameByPublisher(publisherId, page).toDto()
       }

    override suspend fun getGameByCreator(creatorId: Long, page: Int): GameMainInfoDtoList =
        withContext(Dispatchers.IO){
            remoteSourceGames.getGameByCreator(creatorId, page).toDto()
        }

    override suspend fun getGameDescription(gameId: Long): GameDetailsDto =
       withContext(Dispatchers.IO){
           remoteSourceGames.getGameDescription(gameId).toDto(isFavoriteGame(gameId))
       }

    private suspend fun isFavoriteGame(gameId: Long): Boolean {
        var isFavorite = false
        FirebaseService.auth.uid?.let {
            val documentSnapshot = FirebaseService.fireStore
                .collection(Constants.USER_COLLECTION)
                .document(it)
                .get()
                .await()
            val favs = documentSnapshot.get(Constants.FAVORITE_GAMES) as List<Long>
            if (favs.contains(gameId)) isFavorite = true
        }
        return isFavorite
    }

}