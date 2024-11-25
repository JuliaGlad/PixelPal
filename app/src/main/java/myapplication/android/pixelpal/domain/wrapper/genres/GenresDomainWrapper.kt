package myapplication.android.pixelpal.domain.wrapper.genres

import myapplication.android.pixelpal.data.models.genres.GenresList
import myapplication.android.pixelpal.domain.model.genres.GenreDomain
import myapplication.android.pixelpal.domain.model.genres.GenreDomainList
import java.util.stream.Collectors

fun GenresList.toDomain() =
    GenreDomainList(items.stream()
        .map {
            with(it) {
                GenreDomain(
                    id = id,
                    name = name
                )
            }
        }.collect(Collectors.toList())
    )
