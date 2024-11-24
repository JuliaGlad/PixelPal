package myapplication.android.pixelpal.ui.platforms.fragments.store.recycler_view

import myapplication.android.pixelpal.ui.listener.ClickListener

data class StoreModel(
    val id: Int,
    val storeId: Int,
    val title: String,
    val domain: String?,
    val projects: Int?,
    val background: String,
    val listener: ClickListener
)