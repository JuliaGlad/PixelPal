package myapplication.android.pixelpal.ui.game_details.model.wrapper

import myapplication.android.pixelpal.domain.model.creator.CreatorDomainList
import myapplication.android.pixelpal.ui.creators.model.creatores.CreatorUi
import myapplication.android.pixelpal.ui.creators.model.roles.toUi
import myapplication.android.pixelpal.ui.game_details.model.CreatorsGameUiList
import java.util.stream.Collectors

fun CreatorDomainList.toUi() =
    CreatorsGameUiList(
        items.stream()
            .map {
                val roles = it.role.stream().map {role -> role.toUi() }.collect(Collectors.toList())
                with(it){
                    CreatorUi(
                        id,
                        name,
                        roles,
                        gamesCount,
                        image
                    )
                }
            }.collect(Collectors.toList())
    )