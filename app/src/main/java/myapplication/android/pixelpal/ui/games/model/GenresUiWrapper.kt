package myapplication.android.pixelpal.ui.games.model

import myapplication.android.pixelpal.domain.model.genres.GenreDomainList
import java.util.stream.Collectors

fun GenreDomainList.toUi() =
    GenresUiList(
        genres.stream()
            .map {
                with(it) {
                    GenreUi(
                        id = id,
                        title = name
                    )
                }
            }.collect(Collectors.toList())
    )