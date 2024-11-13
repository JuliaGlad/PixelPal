package myapplication.android.pixelpal.domain.wrapper.games

import myapplication.android.pixelpal.data.models.gamesNews.GamesNewsList
import myapplication.android.pixelpal.domain.model.games.GamesNewsDomain
import java.util.stream.Collectors

fun GamesNewsList.toDomain(): List<GamesNewsDomain> =
    items.stream()
        .map {
            with(it) {
                GamesNewsDomain(
                    gameId = id,
                    name = name,
                    releaseDate = releaseDate,
                    genres = genres,
                    uri = image
                )
            }
        }.collect(Collectors.toList())

