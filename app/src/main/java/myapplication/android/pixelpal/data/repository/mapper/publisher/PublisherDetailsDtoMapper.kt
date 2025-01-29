package myapplication.android.pixelpal.data.repository.mapper.publisher

import myapplication.android.pixelpal.data.models.publishers.Publisher
import myapplication.android.pixelpal.data.models.publishers.PublisherDetails
import myapplication.android.pixelpal.data.models.publishers.PublishersList
import myapplication.android.pixelpal.data.repository.dto.publisher.PublisherDetailsDto
import myapplication.android.pixelpal.data.repository.dto.publisher.PublisherDto
import myapplication.android.pixelpal.data.repository.dto.publisher.PublisherDtoList
import java.util.stream.Collectors

fun PublisherDetails.toDto() =
    PublisherDetailsDto(description)
