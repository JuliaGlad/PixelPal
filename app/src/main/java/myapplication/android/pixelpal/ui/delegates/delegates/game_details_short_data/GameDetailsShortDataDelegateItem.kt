package myapplication.android.pixelpal.ui.delegates.delegates.game_details_short_data

import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class GameDetailsShortDataDelegateItem(
    val model: GameDetailsShortDataModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}