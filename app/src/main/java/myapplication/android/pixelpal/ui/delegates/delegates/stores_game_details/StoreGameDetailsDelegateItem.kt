package myapplication.android.pixelpal.ui.delegates.delegates.stores_game_details

import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class StoreGameDetailsDelegateItem(
    val model: StoreGameDetailsModel
) : DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}