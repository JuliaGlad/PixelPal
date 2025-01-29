package myapplication.android.pixelpal.ui.creators.model.roles

import myapplication.android.pixelpal.domain.model.creator.RoleDomain
import java.util.Locale

fun RoleDomain.toUi() =
    RolesUi(
        id = id,
        title = name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.ROOT)
            else it.toString()
        }
    )