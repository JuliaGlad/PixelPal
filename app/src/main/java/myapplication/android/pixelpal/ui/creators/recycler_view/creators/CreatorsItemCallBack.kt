package myapplication.android.pixelpal.ui.creators.recycler_view.creators

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class CreatorsItemCallBack: DiffUtil.ItemCallback<CreatorsModel>() {
    override fun areItemsTheSame(oldItem: CreatorsModel, newItem: CreatorsModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CreatorsModel, newItem: CreatorsModel): Boolean =
        oldItem.hashCode() == newItem.hashCode()
}