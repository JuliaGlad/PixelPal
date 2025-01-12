package myapplication.android.pixelpal.ui.delegates.delegates.rating_image

import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class RatingImageDelegateItem(
    val model: RatingImageModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}