package myapplication.android.pixelpal.ui.creators.model.roles

import myapplication.android.pixelpal.domain.model.creator.CreatorRoleDomain

fun CreatorRoleDomain.toUi() =
    RolesUi(
        id = id,
        title = name
    )