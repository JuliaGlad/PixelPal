package myapplication.android.pixelpal.data.repository.games

import android.util.Log
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

    override suspend fun getGamesShortData(page: Int, genres: Long): GamesShortDomainList {
        Log.i("Id + page", "page: $page genres: $genres")
        val local = localSourceShortGames.getGamesShortData(page, genres)
        val result =
            if (local != null) local
            else {
                val remote = remoteSourceGames.getGamesShortData(page, genres)
                localSourceShortGames.insertGamesShortData(page, remote, genres)
                remote
            }.toDomain()
//        Log.i("Result", result.games.size.toString())
//        for (i in result.games){
//            with(i){
//                Log.i("Result", "g")
//            }
//        }
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
}