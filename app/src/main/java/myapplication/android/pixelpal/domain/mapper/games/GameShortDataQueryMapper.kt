package myapplication.android.pixelpal.domain.mapper.games

import android.util.Log
import myapplication.android.pixelpal.data.repository.dto.game.GamesShortDtoList
import myapplication.android.pixelpal.domain.model.games.GameShortDomain
import myapplication.android.pixelpal.domain.model.games.GamesShortDomainList
import java.util.Locale
import java.util.stream.Collectors

fun GamesShortDtoList.toDomain(query: String) =
    GamesShortDomainList(
        games.stream()
            .filter {
                it.ageRating.name != null && (it.ageRating.name != "Adults Only" || it.ageRating.name != "Rating Pending")
                        && it.name.lowercase(Locale.ROOT).contains(query.lowercase())
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
