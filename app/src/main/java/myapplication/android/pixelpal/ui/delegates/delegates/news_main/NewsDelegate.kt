package myapplication.android.pixelpal.ui.delegates.delegates.news_main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import myapplication.android.pixelpal.databinding.RecyclerViewMainNewsItemBinding
import myapplication.android.pixelpal.ui.delegates.delegates.emptyItems.EmptyItemAdapter
import myapplication.android.pixelpal.ui.delegates.delegates.emptyItems.EmptyItemsModel
import myapplication.android.pixelpal.ui.delegates.main.AdapterDelegate
import myapplication.android.pixelpal.ui.delegates.main.DelegateItem
import myapplication.android.pixelpal.ui.home.recycler_view.releases.ReleasesAdapter
import myapplication.android.pixelpal.ui.home.recycler_view.releases.ReleasesModel
import myapplication.android.pixelpal.ui.listener.PaginationScrollListener

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
            initRecycler(model.newItems, model.items, model.emptyTitle)
            checkRecyclerEnd(model)
        }

        private fun checkRecyclerEnd(
            model: NewsItemModel
        ) {
            var loading = false
            var lastPage = false

            if (model.isUpdated){
                model.setIsUpdated(false)
                loading = false
                lastPage = false
            }

            binding.recyclerView.addOnScrollListener(object : PaginationScrollListener(
                binding.recyclerView.layoutManager!! as LinearLayoutManager
            ) {
                override fun isLastPage(): Boolean = lastPage

                override fun isLoading(): Boolean = loading

                override fun loadMoreItems() {
                    loading = true
                    lastPage = true
                    model.onGetNewItems.onEndReached()
                }
            })
        }

        private fun initRecycler(
            newItems: MutableList<ReleasesModel>,
            items: MutableList<ReleasesModel>,
            emptyTitle: String
        ) {
            if (items.isNotEmpty()) {
                val adapter = ReleasesAdapter()
                binding.recyclerView.adapter = adapter
                adapter.submitList(items)
                if (newItems.isNotEmpty()){
                    val previousPosition = updateRecycler(newItems, items, adapter)
                    binding.recyclerView.scrollToPosition(previousPosition + 1)
                }
            } else {
                val emptyAdapter = EmptyItemAdapter()
                binding.recyclerView.adapter = emptyAdapter
                val item = listOf(
                    EmptyItemsModel(
                        1,
                        emptyTitle
                    )
                )
                emptyAdapter.submitList(item)
            }
        }

        private fun updateRecycler(
            newItems: MutableList<ReleasesModel>,
            items: MutableList<ReleasesModel>,
            adapter: ReleasesAdapter
        ): Int {
            val previousPosition = items.size - 1
            val itemsCount = newItems.size
            items.addAll(newItems)
            newItems.clear()
            adapter.notifyItemRangeInserted(previousPosition, itemsCount)
            return previousPosition
        }
    }
}