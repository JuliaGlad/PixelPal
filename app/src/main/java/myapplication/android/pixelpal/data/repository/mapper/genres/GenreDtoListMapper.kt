package myapplication.android.pixelpal.data.repository.mapper.genres

import myapplication.android.pixelpal.data.models.genres.GenresList
import myapplication.android.pixelpal.data.repository.dto.genre.GenreDto
import myapplication.android.pixelpal.data.repository.dto.genre.GenreDtoList
import java.util.stream.Collectors

fun GenresList.toDto() =
    GenreDtoList(
        items.stream()
            .map {
                with(it) {
                    GenreDto(
                        id = id,
                        name = name
                    )
                }
            }.collect(Collectors.toList())
    )