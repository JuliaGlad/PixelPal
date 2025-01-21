package myapplication.android.pixelpal.ui.all_creators.recycler_view

import androidx.recyclerview.widget.DiffUtil

class AllCreatorsItemCallBack: DiffUtil.ItemCallback<AllCreatorsModel>() {
    override fun areItemsTheSame(oldItem: AllCreatorsModel, newItem: AllCreatorsModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: AllCreatorsModel, newItem: AllCreatorsModel): Boolean =
        oldItem == newItem
}