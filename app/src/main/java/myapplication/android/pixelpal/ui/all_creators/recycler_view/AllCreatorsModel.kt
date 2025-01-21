package myapplication.android.pixelpal.ui.all_creators.recycler_view

import myapplication.android.pixelpal.ui.creators.model.roles.RolesUi
import myapplication.android.pixelpal.ui.listener.ClickListener

data class AllCreatorsModel(
    val id: Int,
    val name: String,
    val roles: List<RolesUi>,
    val famousProjects: Int,
    val image: String?,
    val listener: ClickListener
)