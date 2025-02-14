package myapplication.android.pixelpal.data.repository.mapper.game

import kotlinx.serialization.json.jsonObject
import myapplication.android.pixelpal.data.models.gamesNews.GamesMainInfo
import myapplication.android.pixelpal.data.models.gamesNews.GamesMainInfoList
import myapplication.android.pixelpal.data.repository.dto.game.GameMainInfoDto
import myapplication.android.pixelpal.data.repository.dto.game.GameMainInfoDtoList
import myapplication.android.pixelpal.data.repository.dto.game.GameRatingDto
import java.util.stream.Collectors

fun GamesMainInfoList.toDto() =
    GameMainInfoDtoList(
        items.stream()
            .map { it.toDto() }
            .collect(Collectors.toList())
    )

fun GamesMainInfo.toDto(): GameMainInfoDto {
    val genre =
        if (genres != null) {
            if (genres.isNotEmpty()) {
                genres[0].jsonObject["name"].toString()
            } else "unknown"
        } else genreName
    return GameMainInfoDto(
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