package myapplication.android.pixelpal.data.repository.mapper.genres

import myapplication.android.pixelpal.data.models.genres.GenreDescription
import myapplication.android.pixelpal.data.repository.dto.genre.GenreDescriptionDto

fun GenreDescription.toDto() =
    GenreDescriptionDto(description)