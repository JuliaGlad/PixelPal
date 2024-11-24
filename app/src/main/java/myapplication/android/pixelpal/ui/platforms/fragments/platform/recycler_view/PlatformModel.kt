package myapplication.android.pixelpal.ui.platforms.fragments.platform.recycler_view

import myapplication.android.pixelpal.ui.listener.ClickListener

data class PlatformModel(
    val id: Int,
    val platformId: Long,
    val title: String,
    val startYear: Int?,
    val projects: Int,
    val background: String,
    val listener: ClickListener
)