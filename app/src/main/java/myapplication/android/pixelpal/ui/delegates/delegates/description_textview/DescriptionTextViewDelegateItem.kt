package myapplication.android.pixelpal.ui.delegates.delegates.description_textview

import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class DescriptionTextViewDelegateItem(
    val model: DescriptionTextViewModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}