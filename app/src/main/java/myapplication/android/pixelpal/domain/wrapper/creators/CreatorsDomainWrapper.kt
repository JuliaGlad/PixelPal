package myapplication.android.pixelpal.domain.wrapper.creators

import myapplication.android.pixelpal.data.models.creators.CreatorsList
import myapplication.android.pixelpal.data.models.creators_roles.Role
import myapplication.android.pixelpal.domain.model.creator.CreatorDomain
import myapplication.android.pixelpal.domain.model.creator.CreatorDomainList
import java.util.stream.Collectors

fun CreatorsList.toDomain(requiredId: Int) =
    CreatorDomainList(
        items.stream()
            .filter { getRoleId(it.role).contains(requiredId) }
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


private fun getRoleId(role: List<Role>): List<Int> = role
    .stream()
    .map { it.id }
    .collect(Collectors.toList())