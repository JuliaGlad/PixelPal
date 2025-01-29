package myapplication.android.pixelpal.data.repository.mapper.game

import myapplication.android.pixelpal.data.models.screenshots.Screenshot
import myapplication.android.pixelpal.data.models.screenshots.ScreenshotsList
import myapplication.android.pixelpal.data.repository.dto.game.ScreenshotDto
import myapplication.android.pixelpal.data.repository.dto.game.ScreenshotDtoList
import java.util.stream.Collectors

fun ScreenshotsList.toDto() =
    ScreenshotDtoList(
        items.stream()
            .map { it.toDto() }
            .collect(Collectors.toList())
    )

fun Screenshot.toDto() =
    ScreenshotDto(
        id = id,
        image = image
    )