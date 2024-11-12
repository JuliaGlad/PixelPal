package myapplication.android.pixelpal.home.recycler_view.newsItem

import androidx.recyclerview.widget.DiffUtil

class NewsItemCallBack : DiffUtil.ItemCallback<NewsItemModel>() {
    override fun areItemsTheSame(oldItem: NewsItemModel, newItem: NewsItemModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: NewsItemModel, newItem: NewsItemModel): Boolean =
        oldItem == newItem
}