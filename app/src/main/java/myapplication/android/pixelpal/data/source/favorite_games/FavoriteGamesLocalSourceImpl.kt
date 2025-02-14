package myapplication.android.pixelpal.data.source.favorite_games

import myapplication.android.pixelpal.data.database.provider.FavoriteGamesProvider
import myapplication.android.pixelpal.data.models.gamesNews.GamesMainInfo
import myapplication.android.pixelpal.data.models.gamesNews.GamesMainInfoList
import java.util.stream.Collectors
import javax.inject.Inject

class FavoriteGamesLocalSourceImpl @Inject constructor() : FavoriteGamesLocalSource {
    override fun getFavoriteGames(): GamesMainInfoList? {
        val data = FavoriteGamesProvider().getFavoriteGames()
        return if (data.isNotEmpty()) {
            GamesMainInfoList(
                data.stream()
                    .map {
                        with(it) {
                            GamesMainInfo(
                                id = gameId,
                                name = title,
                                playTime = playTime,
                                genreName = genre,
                                releaseDate = releaseDate,
                                image = image,
                                ageRating = ageRating,
                                rating = rating
                            )
                        }
                    }
                    .collect(Collectors.toList()))
        } else null
    }

    override fun insertFavoriteGames(games: GamesMainInfoList) {
        FavoriteGamesProvider().insertAll(games)
    }

    override fun insertGame(game: GamesMainInfo) {
        FavoriteGamesProvider().insertGame(game)
    }

    override fun deleteGame(id: Long) {
        FavoriteGamesProvider().deleteGame(id)
    }
}