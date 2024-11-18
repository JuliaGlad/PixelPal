package myapplication.android.pixelpal.domain.wrapper.genres

import myapplication.android.pixelpal.data.models.genres.GenreDescription
import myapplication.android.pixelpal.domain.model.genres.GenreDescriptionDomain

fun GenreDescription.toDomain() =
    GenreDescriptionDomain(
        description = description
    )