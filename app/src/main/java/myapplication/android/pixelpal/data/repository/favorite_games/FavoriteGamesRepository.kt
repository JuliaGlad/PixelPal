package myapplication.android.pixelpal.data.repository.favorite_games

import myapplication.android.pixelpal.data.repository.dto.game.GameMainInfoDtoList

interface FavoriteGamesRepository {

    suspend fun getFavoriteGames(): GameMainInfoDtoList

    suspend fun removeGame(gameId: Long)

    suspend fun addGame(gameId: Long)
}