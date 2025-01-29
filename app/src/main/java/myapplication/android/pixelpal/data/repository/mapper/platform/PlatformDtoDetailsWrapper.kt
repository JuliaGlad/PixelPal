package myapplication.android.pixelpal.data.repository.mapper.platform

import myapplication.android.pixelpal.data.models.platforms.PlatformDetails
import myapplication.android.pixelpal.data.repository.dto.platforms.PlatformDtoDetails

fun PlatformDetails.toDto() =
    PlatformDtoDetails(
        endYear = yearEnd,
        description = description
    )