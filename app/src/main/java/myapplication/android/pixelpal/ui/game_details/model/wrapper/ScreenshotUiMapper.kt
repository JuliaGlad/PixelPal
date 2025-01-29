package myapplication.android.pixelpal.ui.game_details.model.wrapper

import myapplication.android.pixelpal.domain.model.screenshot.ScreenShotDomainList
import myapplication.android.pixelpal.ui.game_details.model.ScreenshotUi
import myapplication.android.pixelpal.ui.game_details.model.ScreenshotsUiList
import java.util.stream.Collectors

fun ScreenShotDomainList.toUi() =
    ScreenshotsUiList(
        items.stream()
            .map {
                with(it) {
                    ScreenshotUi(
                        id, image
                    )
                }
            }.collect(Collectors.toList())
    )