package myapplication.android.pixelpal.ui.delegates.delegates.creator_game_details

import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class CreatorGameDetailsDelegateItem(
    val model: CreatorGameDetailsModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}