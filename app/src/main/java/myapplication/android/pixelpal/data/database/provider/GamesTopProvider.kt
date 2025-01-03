package myapplication.android.pixelpal.data.database.provider

import kotlinx.serialization.json.jsonObject
import myapplication.android.pixelpal.app.App.Companion.app
import myapplication.android.pixelpal.data.database.entities.GameTopEntity
import myapplication.android.pixelpal.data.models.gamesNews.GamesNewsList

class GamesTopProvider {

    fun getTopGames(currentPage: Int): List<GameTopEntity>? {
        val data = app.database.gameTopDao().getAll()
        var result: MutableList<GameTopEntity>? = mutableListOf()
        if (data.isEmpty()) result = null
        else {
            for (i in data) {
                with(i) {
                    if (page == currentPage) {
                        result?.add(i)
                    }
                }
            }
        }
        return result
    }

    fun deleteGamesNews() {
        app.database.gameTopDao().deleteAll()
    }

    fun insertGamesNews(games: GamesNewsList, page: Int) {
        val entities = mutableListOf<GameTopEntity>()
        for (i in games.items) {
            with(i) {
                if (!genres.isNullOrEmpty()) {
                    entities.add(
                        GameTopEntity(
                            id,
                            page,
                            name,
                            releaseDate,
                            image,
                            rating,
                            ageRating,
                            genres[0].jsonObject["name"].toString()
                        )
                    )
                }
            }
        }
        app.database.gameTopDao().insertAll(entities)
    }
}