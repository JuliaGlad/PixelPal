package myapplication.android.pixelpal.domain.wrapper.games

import kotlinx.serialization.json.jsonObject
import myapplication.android.pixelpal.data.models.gamesNews.GamesNewsList
import myapplication.android.pixelpal.domain.model.games.GamesNewsDomain
import java.util.stream.Collectors

fun GamesNewsList.toDomain(): List<GamesNewsDomain> = items.stream()
    .filter {
        it.ageRating != null && (it.ageRating.name != "Adults Only" || it.ageRating.name != "Rating Pending")
    }
    .map {
        with(it) {
            GamesNewsDomain(
                gameId = id,
                name = name,
                releaseDate = releaseDate,
                rating = rating,
                genre = genres[0].jsonObject["name"].toString(),
                uri = image
            )
        }
    }.collect(Collectors.toList())
