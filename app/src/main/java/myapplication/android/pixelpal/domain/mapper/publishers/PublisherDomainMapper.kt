package myapplication.android.pixelpal.domain.mapper.publishers

import myapplication.android.pixelpal.data.repository.dto.publisher.PublisherDtoList
import myapplication.android.pixelpal.domain.model.publishers.PublisherDomain
import myapplication.android.pixelpal.domain.model.publishers.PublisherDomainList
import java.util.stream.Collectors

fun PublisherDtoList.toDomain() =
    PublisherDomainList(
        publishers.stream()
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