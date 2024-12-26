package myapplication.android.pixelpal.data.database.provider

import myapplication.android.pixelpal.app.App.Companion.app
import myapplication.android.pixelpal.data.database.entities.GamesShortEntity
import myapplication.android.pixelpal.data.models.gamesMain.GamesShortDataList

class GamesShortProvider {
    fun getGamesShort(page: Int): List<GamesShortEntity> {
        val data = app.database.gamesShortDao().getAll()
        val result = mutableListOf<GamesShortEntity>()
        for (i in data){
            if (i.page == page){
                result.add(i)
            }
        }
        return result
    }

    fun deleteGamesShort() {
        app.database.gamesShortDao().deleteAll()
    }

    fun insertGamesShort(currentPage: Int, games: GamesShortDataList) {
        val entities = mutableListOf<GamesShortEntity>()
        for (i in games.items) {
            with(i) {
                entities.add(
                    GamesShortEntity(
                        id,
                        currentPage,
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