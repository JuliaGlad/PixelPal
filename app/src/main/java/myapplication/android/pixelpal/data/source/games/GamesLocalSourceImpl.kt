package myapplication.android.pixelpal.data.source.games

import myapplication.android.pixelpal.data.database.entities.GameReleaseEntity
import myapplication.android.pixelpal.data.database.entities.GameTopEntity
import myapplication.android.pixelpal.data.database.provider.GameReleasesProvider
import myapplication.android.pixelpal.data.database.provider.GamesTopProvider
import myapplication.android.pixelpal.data.models.gamesNews.GamesMainInfo
import myapplication.android.pixelpal.data.models.gamesNews.GamesMainInfoList
import java.util.stream.Collectors
import javax.inject.Inject

class GamesLocalSourceImpl @Inject constructor() : GamesLocalSource {

    override fun getTopGames(currentPage: Int): GamesMainInfoList? {
        val data = GamesTopProvider().getTopGames(currentPage)
        return if (data?.isNotEmpty() == true) {
            GamesMainInfoList(
                data.stream()
                    .map { it.toGameNews() }
                    .collect(Collectors.toList())
            )
        } else null
    }

    override fun getGameMonthReleases(dates: String, page: Int): GamesMainInfoList? =
        GameReleasesProvider().getGameReleases(false, page)?.toGamesNewsList()

    override fun insertTopGames(games: GamesMainInfoList, currentPage: Int) {
        GamesTopProvider().insertGamesNews(games, currentPage)
    }

    override fun deleteTopGames() {
        GamesTopProvider().deleteGamesNews()
    }

    override fun insertGameReleases(games: GamesMainInfoList, currentPage: Int) {
        GameReleasesProvider().insertGamesReleases(games, currentPage)
    }

    override fun deleteGameReleases() {
        GameReleasesProvider().deleteGamesReleases()
    }

    override fun getGameNewReleases(dates: String, page: Int): GamesMainInfoList? =
        GameReleasesProvider().getGameReleases(true, page)?.toGamesNewsList()


    private fun List<GameReleaseEntity>.toGamesNewsList() =
        GamesMainInfoList(
            stream()
                .map { it.toGameNews() }
                .collect(Collectors.toList())
        )

    private fun GameReleaseEntity.toGameNews() =
        GamesMainInfo(releaseFullDate, image, rating, ageRating,playTime, gameId,  title, null, genres)

    private fun GameTopEntity.toGameNews() =
        GamesMainInfo(releaseDate, image, rating, ageRating, playTime, gameId,  title, null, genres)
}