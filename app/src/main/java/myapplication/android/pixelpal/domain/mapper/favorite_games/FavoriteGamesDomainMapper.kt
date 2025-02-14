package myapplication.android.pixelpal.domain.mapper.favorite_games

import myapplication.android.pixelpal.data.models.favorite_games.FavoriteGameDto
import myapplication.android.pixelpal.domain.model.favorite_games.FavoriteGameDomainModel

fun FavoriteGameDto.toDomain() =
    FavoriteGameDomainModel(gameId = gameId)