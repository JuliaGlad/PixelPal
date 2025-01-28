package myapplication.android.pixelpal.ui.delegates.delegates.title_textview

import android.view.Gravity

data class TitleTextViewModel(
    val id: Int,
    val text: String,
    val gravity: Int = Gravity.START
)