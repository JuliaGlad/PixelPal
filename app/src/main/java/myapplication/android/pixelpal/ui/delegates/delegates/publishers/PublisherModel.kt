package myapplication.android.pixelpal.ui.delegates.delegates.publishers

import myapplication.android.pixelpal.ui.listener.ClickListener

data class PublisherModel(
    val id: Int,
    val publisherId: Long,
    val title: String,
    val projects: Int,
    val background: String?,
    val clickListener: ClickListener
)