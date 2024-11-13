package myapplication.android.pixelpal.domain.wrapper.genres

import myapplication.android.pixelpal.data.models.genres.genres.GenresList
import myapplication.android.pixelpal.domain.model.genres.GenreDomain
import java.util.stream.Collectors

fun GenresList.toDomain() =
    items.stream()
        .map {
            with(it) {
                GenreDomain(
                    id = id,
                    name = name
                )
            }
        }.collect(Collectors.toList())
