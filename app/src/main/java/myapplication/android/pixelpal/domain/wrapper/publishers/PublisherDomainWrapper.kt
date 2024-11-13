package myapplication.android.pixelpal.domain.wrapper.publishers

import myapplication.android.pixelpal.data.models.publishers.PublishersList
import myapplication.android.pixelpal.domain.model.publishers.PublisherDomain
import java.util.stream.Collectors

fun PublishersList.toDomain() =
    items.stream()
        .map {
            with(it){
                PublisherDomain(
                    id = id,
                    name = name,
                    gamesCount = gamesCount,
                    image = image
                )
            }
        }.collect(Collectors.toList())