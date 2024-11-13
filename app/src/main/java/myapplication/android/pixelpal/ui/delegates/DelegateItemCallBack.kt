package myapplication.android.pixelpal.ui.delegates

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import myapplication.android.pixelpal.ui.delegates.DelegateItem

class DelegateItemCallBack : DiffUtil.ItemCallback<DelegateItem>() {
    override fun areItemsTheSame(oldItem: DelegateItem, newItem: DelegateItem): Boolean =
        oldItem::class == newItem::class && oldItem.id() == newItem.id()

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DelegateItem, newItem: DelegateItem): Boolean =
        oldItem.content() == newItem.content()
}