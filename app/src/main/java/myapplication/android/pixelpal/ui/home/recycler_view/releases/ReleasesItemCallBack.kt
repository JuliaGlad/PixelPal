package myapplication.android.pixelpal.ui.home.recycler_view.releases

import androidx.recyclerview.widget.DiffUtil

class ReleasesItemCallBack: DiffUtil.ItemCallback<ReleasesModel>() {
    override fun areItemsTheSame(oldItem: ReleasesModel, newItem: ReleasesModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ReleasesModel, newItem: ReleasesModel): Boolean =
        oldItem == newItem
}