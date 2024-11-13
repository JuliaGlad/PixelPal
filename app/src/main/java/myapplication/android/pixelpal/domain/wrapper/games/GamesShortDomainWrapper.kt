package myapplication.android.pixelpal.domain.wrapper.games

import myapplication.android.pixelpal.data.models.gamesMain.GamesShortDataList
import myapplication.android.pixelpal.domain.model.games.GamesShortDomain
import java.util.stream.Collectors

fun GamesShortDataList.toDomain(): List<GamesShortDomain> =
    items.stream()
        .map {
            with(it) {
                GamesShortDomain(
                    id = id,
                    name = name,
                    releaseDate = releaseDate,
                    genres = genres,
                    playtime = playtime,
                    image = image
                )
            }
        }.collect(Collectors.toList())