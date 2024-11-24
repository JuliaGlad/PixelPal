package myapplication.android.pixelpal.domain.wrapper.publishers

import myapplication.android.pixelpal.data.models.publishers.PublishersList
import myapplication.android.pixelpal.domain.model.publishers.PublisherDomain
import myapplication.android.pixelpal.domain.model.publishers.PublisherDomainList
import java.util.stream.Collectors

fun PublishersList.toDomain() =
    PublisherDomainList(
        items.stream()
            .map {
                with(it) {
                    PublisherDomain(
                        id = id,
                        name = name,
                        gamesCount = gamesCount,
                        image = image
                    )
                }
            }.collect(Collectors.toList())
    )