package myapplication.android.pixelpal.ui.creators.model.creatores

import myapplication.android.pixelpal.domain.model.creator.CreatorDomain
import myapplication.android.pixelpal.ui.creators.model.roles.toUi
import java.util.stream.Collectors

fun CreatorDomain.toUi(): CreatorsUi {

    val rolesUi = role
        .stream()
        .map { role -> role.toUi() }
        .collect(Collectors.toList())

    return CreatorsUi(
        id = id,
        name = name,
        rolesUi,
        famousProjects = gamesCount,
        image = image
    )
}
