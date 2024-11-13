package myapplication.android.pixelpal.domain.wrapper.creators

import myapplication.android.pixelpal.data.models.creators_roles.CreatorsRolesList
import myapplication.android.pixelpal.domain.model.creator.CreatorRoleDomain
import java.util.stream.Collectors

fun CreatorsRolesList.toDomain() =
    items.stream()
        .map {
            with(it) {
                CreatorRoleDomain(
                    id = id,
                    name = name
                )
            }
        }.collect(Collectors.toList())