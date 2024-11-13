package myapplication.android.pixelpal.ui.delegates.delegates

import myapplication.android.pixelpal.ui.delegates.DelegateItem

class NewsDelegateItem(
    private val newsItemModel: NewsItemModel
) : DelegateItem {
    override fun content(): Any = newsItemModel

    override fun id(): Int = newsItemModel.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        this.content() == other.content()

}