package myapplication.android.pixelpal.ui.delegates.delegates.image_view

import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class ImageViewDelegateItem(
    val model: ImageViewModel
) : DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}