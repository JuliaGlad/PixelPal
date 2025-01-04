package myapplication.android.pixelpal.domain.wrapper.screenshots

import myapplication.android.pixelpal.data.models.screenshots.ScreenshotsList
import myapplication.android.pixelpal.domain.model.screenshot.ScreenShotDomainList
import myapplication.android.pixelpal.domain.model.screenshot.ScreenshotDomain
import java.util.stream.Collectors

fun ScreenshotsList.toDomain() =
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