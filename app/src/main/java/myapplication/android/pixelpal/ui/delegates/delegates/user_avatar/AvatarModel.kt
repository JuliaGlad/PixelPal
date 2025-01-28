package myapplication.android.pixelpal.ui.delegates.delegates.user_avatar

import myapplication.android.pixelpal.ui.listener.ClickListener

data class AvatarModel(
    val id: Int,
    var uri: String?,
    val clickListener: ClickListener? = null
)
