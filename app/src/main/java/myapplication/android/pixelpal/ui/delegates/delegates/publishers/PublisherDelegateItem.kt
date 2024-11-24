package myapplication.android.pixelpal.ui.delegates.delegates.publishers

import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class PublisherDelegateItem(
    private val model: PublisherModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}