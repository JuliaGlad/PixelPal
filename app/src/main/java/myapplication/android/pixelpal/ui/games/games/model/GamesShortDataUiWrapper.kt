package myapplication.android.pixelpal.ui.games.games.model

import myapplication.android.pixelpal.domain.model.games.GamesShortDomainList
import java.util.stream.Collectors

fun GamesShortDomainList.toUi() =
    GamesShortDataUiList(
        games.stream()
            .map {
                with(it) {
                    GamesShortDataUi(
                        gameId = id,
                        name = name,
                        rating = rating,
                        releaseDate = releaseDate,
                        playtime = playtime,
                        image = image
                    )
                }
            }.collect(Collectors.toList())
    )