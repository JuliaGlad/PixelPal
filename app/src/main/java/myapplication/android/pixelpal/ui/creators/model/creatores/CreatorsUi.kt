package myapplication.android.pixelpal.ui.creators.model.creatores

import myapplication.android.pixelpal.ui.creators.model.roles.RolesUi

class CreatorsUi(
    val id: Long,
    val name: String,
//    val rating: String,
    val role: List<RolesUi>,
    val famousProjects: Int,
    val image: String
) : Any()