package myapplication.android.pixelpal.data.repository.mapper.game

import kotlinx.serialization.json.jsonObject
import myapplication.android.pixelpal.data.models.gamesNews.GamesNews
import myapplication.android.pixelpal.data.models.gamesNews.GamesNewsList
import myapplication.android.pixelpal.data.repository.dto.game.GameNewsDto
import myapplication.android.pixelpal.data.repository.dto.game.GameNewsDtoList
import myapplication.android.pixelpal.data.repository.dto.game.GameRatingDto
import java.util.stream.Collectors

fun GamesNewsList.toDto() =
    GameNewsDtoList(
        items.stream()
            .map { it.toDto() }
            .collect(Collectors.toList())
    )

fun GamesNews.toDto(): GameNewsDto {
    val genre =
        if (genres != null) {
            if (genres.isNotEmpty()) {
                genres[0].jsonObject["name"].toString()
            } else "unknown"
        } else genreName
    return GameNewsDto(
        gameId = id,
        name = name,
        ageRating = GameRatingDto(ageRating?.name),
        playTime = playTime,
        releaseDate = releaseDate,
        rating = rating,
        genre = genre.orEmpty(),
        uri = image
    )
}