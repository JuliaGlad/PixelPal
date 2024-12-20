package myapplication.android.pixelpal.data.database.provider

import kotlinx.serialization.json.jsonObject
import myapplication.android.pixelpal.app.App.Companion.app
import myapplication.android.pixelpal.data.database.entities.GameReleaseEntity
import myapplication.android.pixelpal.data.models.gamesNews.GamesNewsList

class GameReleasesProvider {
    fun getGameReleases(): List<GameReleaseEntity> =
        app.database.gameReleasesDao().getAll()

    fun deleteGamesReleases() { app.database.gameReleasesDao().deleteAll() }

    fun insertGamesReleases(games: GamesNewsList) {
        val entities = mutableListOf<GameReleaseEntity>()
        for (i in games.items){
            with(i){
                genres?.get(0)?.jsonObject?.get("name")?.let {
                    GameReleaseEntity(
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
        app.database.gameReleasesDao().insertAll(entities)
    }
}