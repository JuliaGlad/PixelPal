package myapplication.android.pixelpal.ui.delegates.delegates.news_main

import myapplication.android.pixelpal.ui.home.recycler_view.releases.ReleasesModel
import myapplication.android.pixelpal.ui.listener.ClickListener
import myapplication.android.pixelpal.ui.listener.RecyclerEndListener

data class NewsItemModel(
    val id: Int,
    val title: String,
    val emptyTitle: String,
    val items: MutableList<ReleasesModel>,
    val listener: ClickListener,
    val onGetNewItems: RecyclerEndListener,
    var isUpdated: Boolean = false
){
    fun setIsUpdated(value: Boolean){
        isUpdated = value
    }
}