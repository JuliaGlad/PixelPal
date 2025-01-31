package myapplication.android.pixelpal.domain.mapper.creators

import android.util.Log
import myapplication.android.pixelpal.data.repository.dto.creators.CreatorRoleDto
import myapplication.android.pixelpal.data.repository.dto.creators.CreatorsDtoList
import myapplication.android.pixelpal.domain.model.creator.CreatorDomain
import myapplication.android.pixelpal.domain.model.creator.CreatorDomainList
import java.util.Locale
import java.util.stream.Collectors

fun CreatorsDtoList.toDomainWithQuery(requiredId: Int, query: String) =
    CreatorDomainList(
        items.stream()
            .filter {
                getRoleId(it.role).contains(requiredId)
                    && it.name.lowercase(Locale.getDefault()).contains(query.lowercase()) }
            .map {
                with(it) {
                    val rolesDomain = role
                        .stream()
                        .map { role -> role.toDomain() }
                        .collect(Collectors.toList())

                    CreatorDomain(
                        id = id,
                        name = name,
                        role = rolesDomain,
                        gamesCount = gamesCount,
                        image = image
                    )
                }
            }.collect(Collectors.toList())
    )

private fun getRoleId(role: List<CreatorRoleDto>): List<Int> = role
    .stream()
    .map { it.id }
    .collect(Collectors.toList())