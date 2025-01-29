package myapplication.android.pixelpal.domain.mapper.games

import myapplication.android.pixelpal.data.repository.dto.game.GameNewsDtoList
import myapplication.android.pixelpal.domain.model.games.GamesNewsDomain
import myapplication.android.pixelpal.domain.model.games.GamesNewsListDomain
import java.util.stream.Collectors

fun GameNewsDtoList.toDomain(): GamesNewsListDomain =
    GamesNewsListDomain(items.stream()
        .filter {
            it.ageRating.name != null && (it.ageRating.name != "Adults Only" || it.ageRating.name != "Rating Pending")
        }
        .map {
            with(it) {
                GamesNewsDomain(
                    gameId = gameId,
                    name = name,
                    playTime = playTime,
                    releaseDate = releaseDate,
                    rating = rating,
                    genre = genre,
                    uri = uri
                )
            }
        }.collect(Collectors.toList())
    )
