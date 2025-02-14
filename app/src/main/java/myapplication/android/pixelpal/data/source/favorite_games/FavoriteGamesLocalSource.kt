package myapplication.android.pixelpal.data.source.favorite_games

import myapplication.android.pixelpal.data.models.gamesNews.GamesMainInfo
import myapplication.android.pixelpal.data.models.gamesNews.GamesMainInfoList

interface FavoriteGamesLocalSource {

    fun getFavoriteGames(): GamesMainInfoList?

    fun insertFavoriteGames(games: GamesMainInfoList)

    fun insertGame(game: GamesMainInfo)

    fun deleteGame(id: Long)
}