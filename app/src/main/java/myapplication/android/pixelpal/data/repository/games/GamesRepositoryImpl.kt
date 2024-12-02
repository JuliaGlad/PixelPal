package myapplication.android.pixelpal.data.repository.games

import myapplication.android.pixelpal.data.source.games.GamesLocalSource
import myapplication.android.pixelpal.data.source.games.GamesRemoteSource
import myapplication.android.pixelpal.domain.model.games.GamesNewsListDomain
import myapplication.android.pixelpal.domain.model.games.GamesShortDomainList
import myapplication.android.pixelpal.domain.wrapper.games.toDomain
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(
    private val localSource: GamesLocalSource,
    private val remoteSource: GamesRemoteSource
) : GamesRepository {

    override suspend fun getGamesShortData(genres: Long): GamesShortDomainList =
        (getLocalGamesShortData() ?: remoteSource.getGamesShortData(genres)).toDomain()

    override suspend fun getTopGames(): GamesNewsListDomain =
        (getLocalTopGames() ?: remoteSource.getTopGames()).toDomain()

    override suspend fun getGameByReleasesDate(date: String): GamesNewsListDomain =
        (getLocalTopGames() ?: remoteSource.getGameByReleasesDate(date)).toDomain()

    override fun getLocalTopGames() =  null

    override fun getLocalGamesShortData() = null

    override fun getLocalGamesByReleaseDate(date: String) = null

}