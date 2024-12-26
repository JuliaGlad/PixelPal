package myapplication.android.pixelpal.data.repository.games

import myapplication.android.pixelpal.domain.model.games.GamesNewsListDomain
import myapplication.android.pixelpal.domain.model.games.GamesShortDomainList

interface GamesRepository {

    suspend fun getGamesShortData(page: Int, genres: Long) : GamesShortDomainList

    suspend fun getTopGames(page: Int): GamesNewsListDomain

    suspend fun getGameNewReleases(dates: String, page: Int): GamesNewsListDomain

    suspend fun getGameMonthReleases(dates: String, page: Int): GamesNewsListDomain
}