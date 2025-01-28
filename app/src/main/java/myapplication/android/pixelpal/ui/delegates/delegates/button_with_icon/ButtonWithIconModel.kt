package myapplication.android.pixelpal.ui.delegates.delegates.button_with_icon

import myapplication.android.pixelpal.ui.listener.ClickListener

data class ButtonWithIconModel(
    val id: Int,
    val title: String,
    val icon: Int,
    val listener: ClickListener
)