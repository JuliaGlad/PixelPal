package myapplication.android.pixelpal.domain.mapper.creators

import myapplication.android.pixelpal.data.repository.dto.creators.CreatorRoleDto
import myapplication.android.pixelpal.domain.model.creator.RoleDomain

fun CreatorRoleDto.toDomain() =
    RoleDomain(
        id = id,
        name = name
    )