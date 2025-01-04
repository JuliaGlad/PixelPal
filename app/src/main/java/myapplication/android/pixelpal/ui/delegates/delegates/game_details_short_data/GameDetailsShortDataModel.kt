package myapplication.android.pixelpal.ui.delegates.delegates.game_details_short_data

import myapplication.android.pixelpal.ui.games.games.recycler_view.GamesShortModel
import myapplication.android.pixelpal.ui.listener.ClickListener
import myapplication.android.pixelpal.ui.listener.RecyclerEndListener

data class GameDetailsShortDataModel(
    val id: Int,
    val title: String,
    val emptyTitle: String,
    val items: MutableList<GamesShortModel>,
    val listener: ClickListener,
    val onGetNewItems: RecyclerEndListener,
    var isUpdated: Boolean = false
)