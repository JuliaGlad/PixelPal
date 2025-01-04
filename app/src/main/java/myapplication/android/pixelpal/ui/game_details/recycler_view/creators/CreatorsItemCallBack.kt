package myapplication.android.pixelpal.ui.game_details.recycler_view.creators

import androidx.recyclerview.widget.DiffUtil
import myapplication.android.pixelpal.ui.delegates.delegates.creators.CreatorsModel

class CreatorsItemCallBack: DiffUtil.ItemCallback<CreatorsModel>() {
    override fun areItemsTheSame(oldItem: CreatorsModel, newItem: CreatorsModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CreatorsModel, newItem: CreatorsModel): Boolean =
        oldItem == newItem
}