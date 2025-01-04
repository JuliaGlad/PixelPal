package myapplication.android.pixelpal.ui.game_details.recycler_view.stores_details

import myapplication.android.pixelpal.ui.listener.ClickListener

data class StoreLinkModels(
    val id: Int,
    val storeId: Long,
    val link: String,
    val listener: ClickListener
)