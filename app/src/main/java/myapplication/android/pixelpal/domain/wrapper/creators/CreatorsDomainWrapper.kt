package myapplication.android.pixelpal.domain.wrapper.creators

import myapplication.android.pixelpal.data.models.creators.CreatorsList
import myapplication.android.pixelpal.domain.model.creator.CreatorDomain
import java.util.stream.Collectors

fun CreatorsList.toDomain() =
    items.stream()
        .map {
            with(it) {
                CreatorDomain(
                    id = id,
                    name = name,
//                    role = role,
//                    rating = rating,
                    gamesCount = gamesCount,
                    image = image
                )
            }
        }.collect(Collectors.toList())