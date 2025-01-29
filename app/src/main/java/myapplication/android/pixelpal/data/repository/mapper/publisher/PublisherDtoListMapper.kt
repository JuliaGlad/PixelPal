package myapplication.android.pixelpal.data.repository.mapper.publisher

import myapplication.android.pixelpal.data.models.publishers.Publisher
import myapplication.android.pixelpal.data.models.publishers.PublishersList
import myapplication.android.pixelpal.data.repository.dto.publisher.PublisherDto
import myapplication.android.pixelpal.data.repository.dto.publisher.PublisherDtoList
import java.util.stream.Collectors

fun PublishersList.toDto() =
    PublisherDtoList(
        items.stream()
            .map { it.toDto() }
            .collect(Collectors.toList())
    )
fun Publisher.toDto() =
    PublisherDto(
        id = id,
        name = name,
        gamesCount = gamesCount,
        image = image
    )