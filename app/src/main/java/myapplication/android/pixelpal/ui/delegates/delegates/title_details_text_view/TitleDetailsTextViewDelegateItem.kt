package myapplication.android.pixelpal.ui.delegates.delegates.title_details_text_view

import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class TitleDetailsTextViewDelegateItem(
    val model: TitleDetailsTextViewModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}