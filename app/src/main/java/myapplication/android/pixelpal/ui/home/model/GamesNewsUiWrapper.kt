package myapplication.android.pixelpal.ui.home.model

import myapplication.android.pixelpal.domain.model.games.GamesNewsListDomain
import java.util.stream.Collectors

fun GamesNewsListDomain.toUi() =
    GamesNewsListUi(items.stream()
        .map {
            with(it) {
                GamesUi(
                    gameId = gameId,
                    name = name,
                    releaseDate = releaseDate,
                    rating = rating,
                    genre = genre,
                    uri = uri
                )
            }
        }.collect(Collectors.toList())
    )
