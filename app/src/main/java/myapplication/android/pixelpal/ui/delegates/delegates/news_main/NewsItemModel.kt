package myapplication.android.pixelpal.ui.delegates.delegates.news_main

import myapplication.android.pixelpal.ui.home.recycler_view.releases.ReleasesModel
import myapplication.android.pixelpal.ui.listener.ClickListener

data class NewsItemModel(
    val id: Int,
    val title: String,
    val emptyTitle: String,
    val items: List<ReleasesModel>,
    val listener: ClickListener
)