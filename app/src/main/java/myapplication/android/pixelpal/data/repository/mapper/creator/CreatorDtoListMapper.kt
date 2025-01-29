package myapplication.android.pixelpal.data.repository.mapper.creator

import myapplication.android.pixelpal.data.models.creators.Creator
import myapplication.android.pixelpal.data.models.creators.CreatorsList
import myapplication.android.pixelpal.data.models.creators_roles.Role
import myapplication.android.pixelpal.data.repository.dto.creators.CreatorDto
import myapplication.android.pixelpal.data.repository.dto.creators.CreatorRoleDto
import myapplication.android.pixelpal.data.repository.dto.creators.CreatorsDtoList
import java.util.stream.Collectors

fun CreatorsList.toDto(): CreatorsDtoList =
    CreatorsDtoList(
        items!!.stream()
            .map { it.toDto() }
            .collect(Collectors.toList())
    )

fun Creator.toDto() =
    CreatorDto(
        id = id,
        name = name,
        role = role.toDto(),
        gamesCount = gamesCount,
        image = image
    )

fun List<Role>.toDto(): List<CreatorRoleDto> =
    stream()
        .map { CreatorRoleDto(it.id, it.name) }
        .collect(Collectors.toList())