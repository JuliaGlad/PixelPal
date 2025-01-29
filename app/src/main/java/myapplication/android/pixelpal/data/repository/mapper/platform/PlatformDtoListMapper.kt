package myapplication.android.pixelpal.data.repository.mapper.platform

import myapplication.android.pixelpal.data.models.platforms.Platform
import myapplication.android.pixelpal.data.models.platforms.PlatformsList
import myapplication.android.pixelpal.data.repository.dto.platforms.PlatformDtoList
import myapplication.android.pixelpal.data.repository.dto.platforms.PlatformsDto
import java.util.stream.Collectors

fun PlatformsList.toDto() =
    PlatformDtoList(
        items.stream()
            .map { it.toDto() }
            .collect(Collectors.toList())
    )

fun Platform.toDto() =
    PlatformsDto(
        id = id,
        name = name,
        gamesCount = gamesCount,
        startYear = startYear,
        image = image
    )