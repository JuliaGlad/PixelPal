package myapplication.android.pixelpal.ui.creators.model.creatores

import myapplication.android.pixelpal.domain.model.creator.CreatorDomain

fun CreatorDomain.toUi() =
    CreatorsUi(
        id = id,
        name = name,
//        rating = rating,
        famousProjects = gamesCount,
        image = image
    )