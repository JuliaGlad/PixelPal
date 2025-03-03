package myapplication.android.pixelpal.data.database.provider

import kotlinx.serialization.json.jsonObject
import myapplication.android.pixelpal.App.Companion.app
import myapplication.android.pixelpal.data.database.entities.GameTopEntity
import myapplication.android.pixelpal.data.models.gamesNews.GamesMainInfoList

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

    fun insertGamesNews(games: GamesMainInfoList, page: Int) {
        val entities = mutableListOf<GameTopEntity>()
        for (i in games.items) {
            with(i) {
                var genre = "???"
                if (!genres.isNullOrEmpty()) genre =  genres[0].jsonObject["name"].toString()
                    entities.add(
                        GameTopEntity(
                            id,
                            page,
                            name,
                            releaseDate,
                            image,
                            rating,
                            ageRating,
                            genre,
                            playTime
                        )
                    )
            }
        }
        app.database.gameTopDao().insertAll(entities)
    }
}