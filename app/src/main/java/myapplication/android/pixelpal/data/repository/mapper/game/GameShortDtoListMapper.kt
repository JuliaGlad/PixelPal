package myapplication.android.pixelpal.data.repository.mapper.game

import myapplication.android.pixelpal.data.models.gamesMain.GameShortData
import myapplication.android.pixelpal.data.models.gamesMain.GamesShortDataList
import myapplication.android.pixelpal.data.repository.dto.game.GameRatingDto
import myapplication.android.pixelpal.data.repository.dto.game.GameShortDto
import myapplication.android.pixelpal.data.repository.dto.game.GamesShortDtoList
import java.util.stream.Collectors

fun GamesShortDataList.toDto() =
    GamesShortDtoList(
        items.stream()
            .map { it.toDto() }
            .collect(Collectors.toList())
    )

fun GameShortData.toDto() =
    GameShortDto(
        id = id,
        name = name,
        rating = rating,
        releaseDate = releaseDate,
        playtime = playtime,
        image = image,
        ageRating = GameRatingDto(ageRating?.name)
    )