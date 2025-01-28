package myapplication.android.pixelpal.ui.delegates.delegates.subtitle_textview

import android.view.Gravity
import android.view.View

data class SubtitleTextViewModel(
    val id: Int,
    var text: String,
    val margin: Int? = null,
    val alignment: Int = View.TEXT_ALIGNMENT_VIEW_START
)