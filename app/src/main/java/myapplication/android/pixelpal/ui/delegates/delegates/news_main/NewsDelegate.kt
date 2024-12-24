package myapplication.android.pixelpal.ui.delegates.delegates.news_main

import android.util.Log
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

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int,
        payloads: MutableList<Any>
    ) {
        Log.i("Items size", (item.content() as NewsItemModel).items.toString())
        (holder as ViewHolder).updateRecycler(
            payloads[0] as MutableList<ReleasesModel>,
            (item.content() as NewsItemModel).items
        )
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is NewsDelegateItem

    private class ViewHolder(private val binding: RecyclerViewMainNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val adapter = ReleasesAdapter()
        var loading = false
        var lastPage = false

        fun bind(model: NewsItemModel) {
            with(binding) {
                title.setShimmerText(model.title)
                actionAll.setOnClickListener { model.listener.onClick() }
            }
            initRecycler(model.items, model.emptyTitle)
            checkRecyclerEnd(model)
        }

        private fun checkRecyclerEnd(
            model: NewsItemModel
        ) {


//            if (model.isUpdated){
//                model.setIsUpdated(false)
//                loading = false
//                lastPage = false
//            }

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
            items: MutableList<ReleasesModel>,
            emptyTitle: String
        ) {
            if (items.isNotEmpty()) {
                binding.recyclerView.adapter = adapter
                adapter.submitList(items)
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


        fun updateRecycler(
            newItems: MutableList<ReleasesModel>,
            items: MutableList<ReleasesModel>
        ) {
            val previousPosition = items.size - 1
            val itemsCount = newItems.size
            items.addAll(newItems)
            adapter.notifyItemRangeInserted(previousPosition, itemsCount)
            loading = false
            lastPage = false
        }
    }
}