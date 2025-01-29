package myapplication.android.pixelpal.domain.mapper.creators

import myapplication.android.pixelpal.data.repository.dto.creators.CreatorRoleDto
import myapplication.android.pixelpal.domain.model.creator.RoleDomain
import java.util.stream.Collectors

fun List<CreatorRoleDto>.toDomain(): List<RoleDomain> =
    stream()
        .map {
            it.toDomain()
        }.collect(Collectors.toList())