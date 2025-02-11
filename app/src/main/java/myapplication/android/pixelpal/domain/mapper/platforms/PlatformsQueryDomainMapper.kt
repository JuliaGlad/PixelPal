package myapplication.android.pixelpal.domain.mapper.platforms

import android.util.Log
import myapplication.android.pixelpal.data.repository.dto.platforms.PlatformDtoList
import myapplication.android.pixelpal.domain.model.platform.PlatformDomainList
import myapplication.android.pixelpal.domain.model.platform.PlatformsDomain
import java.util.stream.Collectors

fun PlatformDtoList.toDomain(query: String) =
    PlatformDomainList(
        platforms.stream()
            .filter { it.name.lowercase().contains(query.lowercase()) }
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
