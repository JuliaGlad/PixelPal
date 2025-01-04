package myapplication.android.pixelpal.ui.game_details.recycler_view.stores_details

import androidx.recyclerview.widget.DiffUtil

class StoreLinkItemCallBack: DiffUtil.ItemCallback<StoreLinkModels>() {
    override fun areItemsTheSame(oldItem: StoreLinkModels, newItem: StoreLinkModels): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: StoreLinkModels, newItem: StoreLinkModels): Boolean =
        oldItem == newItem
}