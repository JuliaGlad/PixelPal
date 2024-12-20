package myapplication.android.pixelpal.data.database.provider

import myapplication.android.pixelpal.app.App.Companion.app
import myapplication.android.pixelpal.data.database.entities.GamesShortEntity
import myapplication.android.pixelpal.data.models.gamesMain.GamesShortDataList

class GamesShortProvider {
    fun getGamesShort(): List<GamesShortEntity> =
        app.database.gamesShortDao().getAll()

    fun deleteGamesShort() { app.database.gamesShortDao().deleteAll() }

    fun insertGamesShort(games: GamesShortDataList) {
        val entities = mutableListOf<GamesShortEntity>()
        for (i in games.items){
            with(i){
                entities.add(
                    GamesShortEntity(
                        id,
                        name,
                        playtime,
                        releaseDate,
                        image,
                        ageRating,
                        rating
                    )
                )
            }
        }
        app.database.gamesShortDao().insertAll(entities)
    }
}