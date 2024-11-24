package myapplication.android.pixelpal.ui.creators.model.publisher

import myapplication.android.pixelpal.domain.model.publishers.PublisherDomainList
import myapplication.android.pixelpal.ui.creators.model.creatores.CreatorsUiList
import java.util.stream.Collectors

fun PublisherDomainList.toUi() =
    CreatorsUiList(
        null,
        publishers.stream()
            .map {
                with(it) {
                    PublisherUi(
                        id = id,
                        name = name,
                        gamesCount = gamesCount,
                        background = image
                    )
                }
            }.collect(Collectors.toList())
    )