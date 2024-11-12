package myapplication.android.pixelpal.home.recycler_view.newsItem

import myapplication.android.pixelpal.listener.ClickListener

data class NewsItemModel(
    val id: Int,
    val title: String,
    val items: List<Any>,
    val listener: ClickListener
)