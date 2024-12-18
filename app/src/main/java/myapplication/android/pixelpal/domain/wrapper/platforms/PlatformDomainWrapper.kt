package myapplication.android.pixelpal.domain.wrapper.platforms

import myapplication.android.pixelpal.data.models.platforms.PlatformsList
import myapplication.android.pixelpal.domain.model.platform.PlatformDomainList
import myapplication.android.pixelpal.domain.model.platform.PlatformsDomain
import java.util.stream.Collectors

fun PlatformsList.toDomain() =
    PlatformDomainList(
        items.stream()
            .map {
                with(it) {
                    PlatformsDomain(
                        id = id,
                        name = name,
                        gamesCount = gamesCount,
                        startYear = startYear,
                        image = image
                    )
                }
            }.collect(Collectors.toList())
    )
