package myapplication.android.pixelpal.data.source.games

import myapplication.android.pixelpal.data.database.entities.GameReleaseEntity
import myapplication.android.pixelpal.data.database.entities.GameTopEntity
import myapplication.android.pixelpal.data.database.provider.GameReleasesProvider
import myapplication.android.pixelpal.data.database.provider.GamesTopProvider
import myapplication.android.pixelpal.data.models.gamesNews.GamesNews
import myapplication.android.pixelpal.data.models.gamesNews.GamesNewsList
import java.util.stream.Collectors
import javax.inject.Inject

class GamesLocalSourceImpl @Inject constructor() : GamesLocalSource {

    private val gameReleasesProvider = GameReleasesProvider()
    private val gameTopProvider = GamesTopProvider()

    override fun getTopGames(): GamesNewsList? {
        val data = gameTopProvider.getTopGames()
        return if (data.isNotEmpty()) {
            GamesNewsList(
                data.stream()
                    .map { it.toGameNews() }
                    .collect(Collectors.toList())
            )
        } else null
    }

    override fun getGameMonthReleases(dates: String): GamesNewsList? =
        gameReleasesProvider.getGameReleases(false)?.toGamesNewsList()

    override fun insertTopGames(games: GamesNewsList) {
        gameTopProvider.insertGamesNews(games)
    }

    override fun deleteTopGames() {
        gameTopProvider.deleteGamesNews()
    }

    override fun insertGameReleases(games: GamesNewsList) {
        gameReleasesProvider.insertGamesReleases(games)
    }

    override fun deleteGameReleases() {
        gameReleasesProvider.deleteGamesReleases()
    }

    override fun getGameNewReleases(dates: String): GamesNewsList? =
        gameReleasesProvider.getGameReleases(true)?.toGamesNewsList()


    private fun List<GameReleaseEntity>.toGamesNewsList() =
        GamesNewsList(
            stream()
                .map { it.toGameNews() }
                .collect(Collectors.toList())
        )

    private fun GameReleaseEntity.toGameNews() =
        GamesNews(releaseFullDate, image, rating, ageRating, gameId, title, null, genres)

    private fun GameTopEntity.toGameNews() =
        GamesNews(releaseDate, image, rating, ageRating, gameId, title, null, genres)
}