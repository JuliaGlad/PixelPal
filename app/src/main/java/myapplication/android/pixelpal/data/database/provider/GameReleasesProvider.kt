package myapplication.android.pixelpal.data.database.provider

import android.icu.util.Calendar
import kotlinx.serialization.json.jsonObject
import myapplication.android.pixelpal.app.App.Companion.app
import myapplication.android.pixelpal.data.database.entities.GameReleaseEntity
import myapplication.android.pixelpal.data.models.gamesNews.GamesNewsList

class GameReleasesProvider {

    fun getGameReleases(isReleased: Boolean, currentPage: Int): List<GameReleaseEntity>? {
        val data: List<GameReleaseEntity> = app.database.gameReleasesDao().getAll()
        var result: MutableList<GameReleaseEntity>? = mutableListOf()
        if (data.isEmpty()) result = null
        else {
            val calendar = Calendar.getInstance()
            val date = calendar.get(Calendar.DATE)
            val number = calendar.get(Calendar.MONTH)

            for (i in data) {
                with(i) {
                    if (monthNumber == number && page == currentPage) {
                        if (isReleased) {
                            if (releaseDate <= date) {
                                result?.add(i)
                            }
                        } else {
                            if (releaseDate >= date) {
                                result?.add(i)
                            }
                        }
                    }
                }
            }
        }
        return result
    }

    fun deleteGamesReleases() {
        app.database.gameReleasesDao().deleteAll()
    }

    fun insertGamesReleases(games: GamesNewsList, page: Int) {
        val entities = mutableListOf<GameReleaseEntity>()
        for (i in games.items) {
            with(i) {
                var genre = "???"
                if (!genres.isNullOrEmpty()) genre = genres[0].jsonObject["name"].toString()
                val date = releaseDate?.subSequence(releaseDate.length - 2, releaseDate.length)
                    val entity = GameReleaseEntity(
                        id,
                        page,
                        name,
                        releaseDate,
                        date.toString().toInt(),
                        Calendar.getInstance().get(Calendar.MONTH),
                        image,
                        rating,
                        ageRating,
                        genre,
                        playTime
                    )
                entities.add(entity)
            }
        }
        app.database.gameReleasesDao().insertAll(entities)
    }
}