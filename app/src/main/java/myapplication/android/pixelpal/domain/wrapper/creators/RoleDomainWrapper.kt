package myapplication.android.pixelpal.domain.wrapper.creators

import myapplication.android.pixelpal.data.models.creators_roles.Role
import myapplication.android.pixelpal.domain.model.creator.RoleDomain

fun Role.toDomain() =
    RoleDomain(
        id = id,
        name = name
    )