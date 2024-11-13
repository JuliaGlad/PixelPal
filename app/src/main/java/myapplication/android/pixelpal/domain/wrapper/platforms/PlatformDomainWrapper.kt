package myapplication.android.pixelpal.domain.wrapper.platforms

import myapplication.android.pixelpal.data.models.platforms.PlatformsList
import myapplication.android.pixelpal.domain.model.platform.PlatformDomain
import java.util.stream.Collectors

fun PlatformsList.toDomain() =
    items.stream()
        .map {
            with(it) {
                PlatformDomain(
                    id = id,
                    name = name,
                    gamesCount = gamesCount,
                    startYear = startYear,
                    image = image
                )
            }
        }.collect(Collectors.toList())