package myapplication.android.pixelpal.ui.delegates.delegates.emptyItems

import androidx.recyclerview.widget.DiffUtil

class EmptyItemCallBack: DiffUtil.ItemCallback<EmptyItemsModel>() {
    override fun areItemsTheSame(oldItem: EmptyItemsModel, newItem: EmptyItemsModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: EmptyItemsModel, newItem: EmptyItemsModel): Boolean =
        oldItem == newItem


}