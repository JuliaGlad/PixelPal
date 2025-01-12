package myapplication.android.pixelpal.ui.creator_details.model

import myapplication.android.pixelpal.ui.creators.model.roles.RolesUi

class CreatorArgumentsModel(
    val creatorId: Long,
    val name: String,
    val role: Array<String>,
    val famousProjects: Int,
    val image: String?
)