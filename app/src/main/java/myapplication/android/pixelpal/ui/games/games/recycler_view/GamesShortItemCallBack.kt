package myapplication.android.pixelpal.ui.games.games.recycler_view

import androidx.recyclerview.widget.DiffUtil

class GamesShortItemCallBack: DiffUtil.ItemCallback<GamesShortModel>() {
    override fun areItemsTheSame(oldItem: GamesShortModel, newItem: GamesShortModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: GamesShortModel, newItem: GamesShortModel): Boolean =
        oldItem.hashCode() == newItem.hashCode()
}