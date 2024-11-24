package myapplication.android.pixelpal.ui.creators.model.creatores

import myapplication.android.pixelpal.ui.creators.model.roles.RolesUi

class CreatorsUiList(
   val items: List<CreatorUi>
)
class CreatorUi(
    val id: Long,
    val name: String,
    val role: List<RolesUi>,
    val famousProjects: Int,
    val image: String
)