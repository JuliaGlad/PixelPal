package myapplication.android.pixelpal.data.repository.mapper.creator

import myapplication.android.pixelpal.data.models.creators.CreatorDetails
import myapplication.android.pixelpal.data.repository.dto.creators.CreatorDetailsDto

fun CreatorDetails.toDto() =
    CreatorDetailsDto(
        description = description,
        rating = rating
    )