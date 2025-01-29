package myapplication.android.pixelpal.ui.platforms.fragments.platform.model

import myapplication.android.pixelpal.domain.model.platform.PlatformDomainList
import java.util.stream.Collectors

fun PlatformDomainList.toUi() =
    PlatformUiList(
        platforms.stream()
            .map {
                with(it) {
                    PlatformsUi(
                        id = id,
                        name = name,
                        gamesCount = gamesCount,
                        startYear = startYear,
                        background = image
                    )
                }
            }.collect(Collectors.toList())
    )