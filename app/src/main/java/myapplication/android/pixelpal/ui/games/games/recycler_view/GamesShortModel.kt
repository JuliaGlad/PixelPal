package myapplication.android.pixelpal.ui.games.games.recycler_view

import myapplication.android.pixelpal.ui.listener.ClickListener

data class GamesShortModel(
    val id: Long,
    val name: String,
    val rating: Int?,
    val releaseDate: String?,
    val playtime: Int,
    val image: String?,
    val listener: ClickListener,
    var isUpdated: Boolean = false
)