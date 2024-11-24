package myapplication.android.pixelpal.ui.platforms.fragments.platform.recycler_view

import androidx.recyclerview.widget.DiffUtil

class PlatformItemCallBack: DiffUtil.ItemCallback<PlatformModel>() {
    override fun areItemsTheSame(oldItem: PlatformModel, newItem: PlatformModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PlatformModel, newItem: PlatformModel): Boolean =
        oldItem.hashCode() == newItem.hashCode()
}