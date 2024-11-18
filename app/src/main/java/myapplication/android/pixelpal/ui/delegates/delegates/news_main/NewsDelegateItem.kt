package myapplication.android.pixelpal.ui.delegates.delegates.news_main

import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class NewsDelegateItem(
    private val newsItemModel: NewsItemModel
) : DelegateItem {
    override fun content(): Any = newsItemModel

    override fun id(): Int = newsItemModel.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        this.content() == other.content()

}