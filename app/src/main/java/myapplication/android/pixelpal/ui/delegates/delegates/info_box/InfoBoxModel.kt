package myapplication.android.pixelpal.ui.delegates.delegates.info_box

import myapplication.android.pixelpal.ui.listener.ClickListener

data class InfoBoxModel(
    val id: Int,
    val title: String,
    val listener: ClickListener
)