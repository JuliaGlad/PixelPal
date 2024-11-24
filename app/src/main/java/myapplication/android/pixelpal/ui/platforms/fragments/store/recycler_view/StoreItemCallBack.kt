package myapplication.android.pixelpal.ui.platforms.fragments.store.recycler_view

import androidx.recyclerview.widget.DiffUtil

class StoreItemCallBack: DiffUtil.ItemCallback<StoreModel>() {
    override fun areItemsTheSame(oldItem: StoreModel, newItem: StoreModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: StoreModel, newItem: StoreModel): Boolean =
        oldItem.hashCode() == newItem.hashCode()
}