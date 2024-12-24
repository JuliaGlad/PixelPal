package myapplication.android.pixelpal.ui.delegates.delegates.creators

import myapplication.android.pixelpal.ui.listener.ClickListener

data class CreatorsModel(
    val id: Int,
    val creatorId: Long,
    val name: String,
    val famousProjects: Int,
    val roles: List<String>,
    val image: String?,
    val clickListener: ClickListener
)