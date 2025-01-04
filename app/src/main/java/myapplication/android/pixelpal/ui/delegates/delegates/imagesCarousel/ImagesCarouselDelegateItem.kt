package myapplication.android.pixelpal.ui.delegates.delegates.imagesCarousel

import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class ImagesCarouselDelegateItem(
    val model: ImagesCarouselModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()

}