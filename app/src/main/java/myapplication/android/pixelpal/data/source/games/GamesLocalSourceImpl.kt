package myapplication.android.pixelpal.data.source.games

import android.util.Log
import com.google.gson.JsonSerializer
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonEncoder
import myapplication.android.pixelpal.data.database.converters.JsonArrayConverter
import myapplication.android.pixelpal.data.database.entities.GameReleaseEntity
import myapplication.android.pixelpal.data.database.entities.GameTopEntity
import myapplication.android.pixelpal.data.database.entities.GamesShortEntity
import myapplication.android.pixelpal.data.database.provider.GameReleasesProvider
import myapplication.android.pixelpal.data.database.provider.GamesTopProvider
import myapplication.android.pixelpal.data.database.provider.GamesShortProvider
import myapplication.android.pixelpal.data.models.gamesMain.GameShortData
import myapplication.android.pixelpal.data.models.gamesMain.GamesShortDataList
import myapplication.android.pixelpal.data.models.gamesNews.GamesNews
import myapplication.android.pixelpal.data.models.gamesNews.GamesNewsList
import java.util.stream.Collectors
import javax.inject.Inject

class GamesLocalSourceImpl @Inject constructor() : GamesLocalSource {

    override fun getTopGames(): GamesNewsList? {
        val data = GamesTopProvider().getTopGames()
        return if (data.isNotEmpty()) {
            GamesNewsList(
                data.stream()
                    .map { it.toGameNews() }
                    .collect(Collectors.toList())
            )
        } else null
    }

    override fun insertTopGames(games: GamesNewsList) {
        GamesTopProvider().insertGamesNews(games)
    }

    override fun deleteTopGames() {
        GamesTopProvider().deleteGamesNews()
    }

    override fun insertGameReleases(games: GamesNewsList) {
        GameReleasesProvider().insertGamesReleases(games)
    }

    override fun deleteGameReleases() {
        GameReleasesProvider().deleteGamesReleases()
    }

    override fun getGameReleases(): GamesNewsList? {
        val data = GameReleasesProvider().getGameReleases()
        return if (data.isNotEmpty()) {
            GamesNewsList(
                data.stream()
                    .map { it.toGameNews() }
                    .collect(Collectors.toList())
            )
        } else null
    }

    private fun GameReleaseEntity.toGameNews() =
        GamesNews(releaseDate, image, rating, ageRating, gameId, title, null, genres)



    private fun GameTopEntity.toGameNews() =
        GamesNews(releaseDate, image, rating, ageRating, gameId, title, null, genres)
}