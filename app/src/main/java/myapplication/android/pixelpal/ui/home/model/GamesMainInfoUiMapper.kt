package myapplication.android.pixelpal.ui.home.model

import myapplication.android.pixelpal.domain.model.games.GamesMainInfoListDomain
import java.util.stream.Collectors

fun GamesMainInfoListDomain.toUi() =
    GamesMainInfoListUi(items.stream()
        .map {
            with(it) {
                GamesUi(
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
