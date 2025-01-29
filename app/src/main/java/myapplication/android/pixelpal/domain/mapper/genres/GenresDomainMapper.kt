package myapplication.android.pixelpal.domain.mapper.genres

import myapplication.android.pixelpal.data.repository.dto.genre.GenreDtoList
import myapplication.android.pixelpal.domain.model.genres.GenreDomain
import myapplication.android.pixelpal.domain.model.genres.GenreDomainList
import java.util.stream.Collectors

fun GenreDtoList.toDomain() =
    GenreDomainList(genres.stream()
        .map {
            with(it) {
                GenreDomain(
                    id = id,
                    name = name
                )
            }
        }.collect(Collectors.toList())
    )
