package myapplication.android.pixelpal.data.repository.games

import myapplication.android.pixelpal.data.source.games.GamesLocalSource
import myapplication.android.pixelpal.data.source.games.GamesRemoteSource
import myapplication.android.pixelpal.domain.model.games.GamesNewsDomain
import myapplication.android.pixelpal.domain.model.games.GamesShortDomain
import myapplication.android.pixelpal.domain.wrapper.games.toDomain

class GamesRepositoryImpl(
    private val localSource: GamesLocalSource,
    private val remoteSource: GamesRemoteSource
) : GamesRepository {

    override suspend fun getGamesShortData(): List<GamesShortDomain> =
        (getLocalGamesShortData() ?: remoteSource.getGamesShortData()).toDomain()

    override suspend fun getTopGames(): List<GamesNewsDomain> =
        (getLocalTopGames() ?: remoteSource.getTopGames()).toDomain()

    override suspend fun getGameByReleasesDate(date: String): List<GamesNewsDomain> =
        (getLocalTopGames() ?: remoteSource.getGameByReleasesDate(date)).toDomain()

    override fun getLocalTopGames() = null

    override fun getLocalGamesShortData() = null

    override fun getLocalGamesByReleaseDate(date: String) = null

}