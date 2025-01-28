package myapplication.android.pixelpal.ui.delegates.delegates.button_with_icon_warning

import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class ButtonWithIconWarningDelegateItem(
    val model: ButtonWithIconWarningModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}