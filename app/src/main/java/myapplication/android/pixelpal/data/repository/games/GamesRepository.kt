package myapplication.android.pixelpal.data.repository.games

import myapplication.android.pixelpal.domain.model.games.GamesNewsListDomain
import myapplication.android.pixelpal.domain.model.games.GamesShortDomainList

interface GamesRepository {

    suspend fun getGamesShortData(genres: Long) : GamesShortDomainList

    suspend fun getTopGames(): GamesNewsListDomain

    suspend fun getGameNewReleases(dates: String): GamesNewsListDomain

    suspend fun getGameMonthReleases(dates: String): GamesNewsListDomain
}