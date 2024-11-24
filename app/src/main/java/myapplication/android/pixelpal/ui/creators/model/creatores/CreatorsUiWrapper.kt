package myapplication.android.pixelpal.ui.creators.model.creatores

import myapplication.android.pixelpal.domain.model.creator.CreatorDomain
import myapplication.android.pixelpal.domain.model.creator.CreatorDomainList
import myapplication.android.pixelpal.ui.creators.model.roles.RolesUi
import myapplication.android.pixelpal.ui.creators.model.roles.toUi
import java.util.stream.Collectors

fun CreatorDomainList.toUi(): CreatorsUiList =
    CreatorsUiList(
        items.stream()
            .map {
                with(it) {
                    val rolesUi = getRole()
                    CreatorUi(
                        id = id,
                        name = name,
                        rolesUi,
                        famousProjects = gamesCount,
                        image = image
                    )
                }
            }.collect(Collectors.toList())
    )

private fun CreatorDomain.getRole(): MutableList<RolesUi> {
    val rolesUi = role
        .stream()
        .map { role -> role.toUi() }
        .collect(Collectors.toList())
    return rolesUi
}

