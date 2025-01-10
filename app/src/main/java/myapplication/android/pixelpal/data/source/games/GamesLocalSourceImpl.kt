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

    override fun getTopGames(currentPage: Int): GamesNewsList? {
        val data = gameTopProvider.getTopGames(currentPage)
        return if (data?.isNotEmpty() == true) {
            GamesNewsList(
                data.stream()
                    .map { it.toGameNews() }
                    .collect(Collectors.toList())
            )
        } else null
    }

    override fun getGameMonthReleases(dates: String, page: Int): GamesNewsList? =
        gameReleasesProvider.getGameReleases(false, page)?.toGamesNewsList()

    override fun insertTopGames(games: GamesNewsList, currentPage: Int) {
        gameTopProvider.insertGamesNews(games, currentPage)
    }

    override fun deleteTopGames() {
        gameTopProvider.deleteGamesNews()
    }

    override fun insertGameReleases(games: GamesNewsList, currentPage: Int) {
        gameReleasesProvider.insertGamesReleases(games, currentPage)
    }

    override fun deleteGameReleases() {
        gameReleasesProvider.deleteGamesReleases()
    }

    override fun getGameNewReleases(dates: String, page: Int): GamesNewsList? =
        gameReleasesProvider.getGameReleases(true, page)?.toGamesNewsList()


    private fun List<GameReleaseEntity>.toGamesNewsList() =
        GamesNewsList(
            stream()
                .map { it.toGameNews() }
                .collect(Collectors.toList())
        )

    private fun GameReleaseEntity.toGameNews() =
        GamesNews(releaseFullDate, image, rating, ageRating,playTime, gameId,  title, null, genres)

    private fun GameTopEntity.toGameNews() =
        GamesNews(releaseDate, image, rating, ageRating, playTime, gameId,  title, null, genres)
}