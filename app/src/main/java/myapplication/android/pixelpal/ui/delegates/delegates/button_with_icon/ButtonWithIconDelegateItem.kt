package myapplication.android.pixelpal.ui.delegates.delegates.button_with_icon

import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class ButtonWithIconDelegateItem(
    val model: ButtonWithIconModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}