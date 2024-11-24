package myapplication.android.pixelpal.ui.delegates.delegates.creators

import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class CreatorsDelegateItem(
    private val model: CreatorsModel
): DelegateItem {

    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}