package myapplication.android.pixelpal.ui.delegates.delegates.info_box

import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class InfoBoxDelegateItem(
    val model: InfoBoxModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean = content() == other.content()
}