package myapplication.android.pixelpal.domain.wrapper.games

import myapplication.android.pixelpal.data.models.gamesMain.GamesShortDataList
import myapplication.android.pixelpal.domain.model.games.GameShortDomain
import myapplication.android.pixelpal.domain.model.games.GamesShortDomainList
import java.util.stream.Collectors

fun GamesShortDataList.toDomain() =
    GamesShortDomainList(
        items.stream()
            .filter {
                it.ageRating != null && (it.ageRating.name != "Adults Only" || it.ageRating.name != "Rating Pending")
            }.map {
                with(it) {
                    GameShortDomain(
                        id = id,
                        name = name,
                        releaseDate = releaseDate,
                        playtime = playtime,
                        rating = rating,
                        image = image
                    )
                }
            }.collect(Collectors.toList())
    )
