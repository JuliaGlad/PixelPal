package myapplication.android.pixelpal.ui.delegates.delegates

import myapplication.android.pixelpal.ui.listener.ClickListener

data class NewsItemModel(
    val id: Int,
    val title: String,
    val items: List<Any>,
    val listener: ClickListener
)