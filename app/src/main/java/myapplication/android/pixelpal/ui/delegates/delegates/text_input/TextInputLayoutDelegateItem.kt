package myapplication.android.pixelpal.ui.delegates.delegates.text_input

import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class TextInputLayoutDelegateItem(
    val model: TextInputLayoutModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}