package myapplication.android.pixelpal.ui.delegates.delegates.title_textview

import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class TitleTextViewDelegateItem(
    val model: TitleTextViewModel
) : DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean = content() == other.content()
}