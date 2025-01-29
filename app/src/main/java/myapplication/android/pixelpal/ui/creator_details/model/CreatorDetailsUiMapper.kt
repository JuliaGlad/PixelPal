package myapplication.android.pixelpal.ui.creator_details.model

import myapplication.android.pixelpal.domain.model.creator.CreatorDetailsDomain

fun CreatorDetailsDomain.toUi() =
    CreatorDetailsUi(
        description = description,
        rating = rating
    )