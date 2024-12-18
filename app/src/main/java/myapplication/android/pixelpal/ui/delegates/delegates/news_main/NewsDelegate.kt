package myapplication.android.pixelpal.ui.delegates.delegates.news_main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import myapplication.android.pixelpal.databinding.RecyclerViewMainNewsItemBinding
import myapplication.android.pixelpal.ui.delegates.delegates.emptyItems.EmptyItemAdapter
import myapplication.android.pixelpal.ui.delegates.delegates.emptyItems.EmptyItemsModel
import myapplication.android.pixelpal.ui.home.recycler_view.releases.ReleasesAdapter
import myapplication.android.pixelpal.ui.home.recycler_view.releases.ReleasesModel
import myapplication.android.pixelpal.ui.delegates.main.AdapterDelegate
import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class NewsDelegate : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewMainNewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind(item.content() as NewsItemModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is NewsDelegateItem

    private class ViewHolder(private val binding: RecyclerViewMainNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: NewsItemModel) {
            with(binding) {
                title.setShimmerText(model.title)
                actionAll.setOnClickListener { model.listener.onClick() }
            }
            initRecycler(model.items, model.emptyTitle)
        }

        private fun initRecycler(items: List<ReleasesModel>, emptyTitle: String) {
            if (items.isNotEmpty()) {
                val adapter = ReleasesAdapter()
                binding.recyclerView.adapter = adapter
                adapter.submitList(items)
            } else {
                val adapter = EmptyItemAdapter()
                binding.recyclerView.adapter = adapter
                val item = listOf(EmptyItemsModel(
                    1,
                    emptyTitle
                ))
                adapter.submitList(item)
            }
        }
    }
}