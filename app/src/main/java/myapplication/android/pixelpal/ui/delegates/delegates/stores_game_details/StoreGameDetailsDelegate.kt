package myapplication.android.pixelpal.ui.delegates.delegates.stores_game_details

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import myapplication.android.pixelpal.databinding.RecyclerViewStoresMainItemBinding
import myapplication.android.pixelpal.ui.delegates.delegates.emptyItems.EmptyItemAdapter
import myapplication.android.pixelpal.ui.delegates.delegates.emptyItems.EmptyItemsModel
import myapplication.android.pixelpal.ui.delegates.delegates.news_main.NewsItemModel
import myapplication.android.pixelpal.ui.delegates.main.AdapterDelegate
import myapplication.android.pixelpal.ui.delegates.main.DelegateItem
import myapplication.android.pixelpal.ui.game_details.recycler_view.stores_details.StoreLinkAdapter
import myapplication.android.pixelpal.ui.game_details.recycler_view.stores_details.StoreLinkModels
import myapplication.android.pixelpal.ui.listener.LinearPaginationScrollListener

class StoreGameDetailsDelegate: AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewStoresMainItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind((item.content() as StoreGameDetailsModel))
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int,
        payloads: MutableList<Any>
    ) {
        Log.i("Items size", (item.content() as NewsItemModel).items.toString())
        (holder as ViewHolder).updateRecycler(
            payloads[0] as MutableList<StoreLinkModels>,
            (item.content() as StoreGameDetailsModel).items
        )
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is StoreGameDetailsDelegateItem

    class ViewHolder(private val binding: RecyclerViewStoresMainItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val adapter = StoreLinkAdapter()
        var loading = false
        var lastPage = false

        fun bind(model: StoreGameDetailsModel) {
            with(binding) {
                title.setShimmerText(model.title)
            }
            initRecycler(model.items, model.emptyTitle)
            checkRecyclerEnd(model)
        }

        private fun checkRecyclerEnd(
            model: StoreGameDetailsModel
        ) {
            binding.recyclerView.addOnScrollListener(object : LinearPaginationScrollListener(
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
            items: MutableList<StoreLinkModels>,
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
            newItems: MutableList<StoreLinkModels>,
            items: MutableList<StoreLinkModels>
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