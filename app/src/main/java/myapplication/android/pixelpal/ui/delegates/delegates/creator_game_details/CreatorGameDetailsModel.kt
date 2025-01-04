package myapplication.android.pixelpal.ui.delegates.delegates.creator_game_details

import myapplication.android.pixelpal.ui.delegates.delegates.creators.CreatorsModel
import myapplication.android.pixelpal.ui.listener.ClickListener
import myapplication.android.pixelpal.ui.listener.RecyclerEndListener

data class CreatorGameDetailsModel(
    val id: Int,
    val title: String,
    val emptyTitle: String,
    val items: MutableList<CreatorsModel>,
    val listener: ClickListener,
    val onGetNewItems: RecyclerEndListener,
    var isUpdated: Boolean = false
)