package myapplication.android.pixelpal.data.repository.games

import android.util.Log
import myapplication.android.pixelpal.data.repository.getAndCheckData
import myapplication.android.pixelpal.data.source.games.GamesLocalSource
import myapplication.android.pixelpal.data.source.games.GamesRemoteSource
import myapplication.android.pixelpal.data.source.games.GamesShortDataLocalSource
import myapplication.android.pixelpal.domain.model.games.GamesNewsListDomain
import myapplication.android.pixelpal.domain.model.games.GamesShortDomainList
import myapplication.android.pixelpal.domain.wrapper.games.toDomain
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(
    private val localSourceGames: GamesLocalSource,
    private val remoteSourceGames: GamesRemoteSource,
    private val localSourceShortGames: GamesShortDataLocalSource,
) : GamesRepository {

    override suspend fun getGamesShortData(genres: Long): GamesShortDomainList {
        val local = localSourceShortGames.getGamesShortData()
        val result =
            if (local != null) local
            else {
                val remote = remoteSourceGames.getGamesShortData(genres)
                localSourceShortGames.insertGamesShortData(remote)
                remote
            }.toDomain()
        return result
    }

    override suspend fun getTopGames(): GamesNewsListDomain =
        getAndCheckData(
            localSourceGames::getTopGames,
            remoteSourceGames::getTopGames,
            localSourceGames::insertTopGames
        ).toDomain()

    override suspend fun getGameByReleasesDate(date: String): GamesNewsListDomain {
        val local = localSourceGames.getGameReleases()
        Log.i("Local", local.toString())
        return if (local != null) local
        else {
            val remote = remoteSourceGames.getGameByReleasesDate(date)
            localSourceGames.insertGameReleases(remote)
            remote
        }.toDomain()
    }
}