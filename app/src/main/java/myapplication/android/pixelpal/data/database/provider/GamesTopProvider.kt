package myapplication.android.pixelpal.data.database.provider

import kotlinx.serialization.json.jsonObject
import myapplication.android.pixelpal.app.App.Companion.app
import myapplication.android.pixelpal.data.database.entities.GameTopEntity
import myapplication.android.pixelpal.data.models.gamesNews.GamesNewsList

class GamesTopProvider {

    fun getTopGames(): List<GameTopEntity> =
        app.database.gameTopDao().getAll()

    fun deleteGamesNews() {
        app.database.gameTopDao().deleteAll()
    }

    fun insertGamesNews(games: GamesNewsList) {
        val entities = mutableListOf<GameTopEntity>()
        for (i in games.items) {
            with(i) {
                genres?.get(0)?.jsonObject?.get("name")?.let {
                    GameTopEntity(
                        id,
                        name,
                        releaseDate,
                        image,
                        rating,
                        ageRating,
                        it.toString()
                    )
                }?.let {
                    entities.add(
                        it
                    )
                }
            }
        }
        app.database.gameTopDao().insertAll(entities)
    }
}