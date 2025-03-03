package myapplication.android.pixelpal.data.database.provider

import kotlinx.serialization.json.jsonObject
import myapplication.android.pixelpal.App.Companion.app
import myapplication.android.pixelpal.data.database.entities.FavoriteGamesEntity
import myapplication.android.pixelpal.data.models.gamesNews.GamesMainInfo
import myapplication.android.pixelpal.data.models.gamesNews.GamesMainInfoList
import java.util.stream.Collectors

class FavoriteGamesProvider {

    fun getFavoriteGames(): List<FavoriteGamesEntity> =
        app.database.favoriteGamesDao().getGames()

    fun deleteAll() {
        app.database.favoriteGamesDao().deleteAll()
    }

    fun insertGame(game: GamesMainInfo) {
        with(game) {
            var genre = "???"
            if (!genres.isNullOrEmpty()) genre = genres[0].jsonObject["name"].toString()
            app.database.favoriteGamesDao()
                .insertGame(
                    FavoriteGamesEntity(
                        gameId = id,
                        title = name,
                        playTime = playTime,
                        releaseDate = releaseDate,
                        image = image,
                        ageRating = ageRating,
                        rating = rating,
                        genre = genre
                    )
                )
        }
    }

    fun insertAll(games: GamesMainInfoList) {
        app.database.favoriteGamesDao()
            .insertAll(
                games.items.stream()
                    .map {
                        with(it) {
                            var genre = "???"
                            if (!genres.isNullOrEmpty()) genre =
                                genres[0].jsonObject["name"].toString()
                            FavoriteGamesEntity(
                                gameId = id,
                                title = name,
                                playTime = playTime,
                                releaseDate = releaseDate,
                                image = image,
                                ageRating = ageRating,
                                rating = rating,
                                genre = genre
                            )
                        }
                    }
                    .collect(Collectors.toList())
            )
    }

    fun deleteGame(id: Long) {
        val dao = app.database.favoriteGamesDao()
        val games = dao.getGames()
        for (i in games) {
            if (i.gameId == id) {
                dao.deleteGame(i)
                break
            }
        }
    }

}