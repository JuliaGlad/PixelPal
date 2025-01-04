package myapplication.android.pixelpal.ui.delegates.delegates.stores_game_details

import myapplication.android.pixelpal.ui.game_details.recycler_view.stores_details.StoreLinkModels
import myapplication.android.pixelpal.ui.listener.ClickListener
import myapplication.android.pixelpal.ui.listener.RecyclerEndListener

data class StoreGameDetailsModel(
    val id: Int,
    val title: String,
    val emptyTitle: String,
    val items: MutableList<StoreLinkModels>,
    val listener: ClickListener,
    val onGetNewItems: RecyclerEndListener,
    var isUpdated: Boolean = false
)