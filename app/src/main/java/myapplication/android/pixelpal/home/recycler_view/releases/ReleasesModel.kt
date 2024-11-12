package myapplication.android.pixelpal.home.recycler_view.releases

import myapplication.android.pixelpal.home.recycler_view.NewsItem
import myapplication.android.pixelpal.listener.ClickListener

data class ReleasesModel(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val genres: String,
    val uri: String,
    val listener: ClickListener
) : NewsItem()