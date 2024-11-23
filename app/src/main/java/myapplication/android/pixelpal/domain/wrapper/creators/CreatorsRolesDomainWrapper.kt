package myapplication.android.pixelpal.domain.wrapper.creators

import myapplication.android.pixelpal.data.models.creators_roles.RolesList
import myapplication.android.pixelpal.domain.model.creator.RoleDomain
import java.util.stream.Collectors

fun RolesList.toDomain() =
    items.stream()
        .map {
            it.toDomain()
        }.collect(Collectors.toList())