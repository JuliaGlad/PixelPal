package myapplication.android.pixelpal.data.repository.mapper.creator

import myapplication.android.pixelpal.data.models.creators_roles.RolesList
import myapplication.android.pixelpal.data.repository.dto.creators.CreatorRoleDto
import java.util.stream.Collectors

fun RolesList.toDto(): List<CreatorRoleDto> =
    items.stream()
        .map {
            with(it) {
                CreatorRoleDto(
                    id = id,
                    name = name
                )
            }
        }.collect(Collectors.toList())