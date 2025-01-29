package myapplication.android.pixelpal.domain.mapper.screenshots

import myapplication.android.pixelpal.data.repository.dto.game.ScreenshotDtoList
import myapplication.android.pixelpal.domain.model.screenshot.ScreenShotDomainList
import myapplication.android.pixelpal.domain.model.screenshot.ScreenshotDomain
import java.util.stream.Collectors

fun ScreenshotDtoList.toDomain() =
    ScreenShotDomainList(
        items.stream()
            .map {
                with(it){
                    ScreenshotDomain(
                        id, image
                    )
                }
            }.collect(Collectors.toList())
    )