package myapplication.android.pixelpal.ui.creators.model.creatores

import myapplication.android.pixelpal.ui.creators.model.publisher.PublisherUi
import myapplication.android.pixelpal.ui.creators.model.roles.RolesUi

class CreatorsUiList(
    val creators: List<CreatorUi>? = null,
    val publishers: List<PublisherUi>? = null
)

class CreatorUi(
    val id: Long,
    val name: String,
    val role: List<RolesUi>,
    val famousProjects: Int,
    val image: String
)