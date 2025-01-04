package myapplication.android.pixelpal.ui.delegates.delegates.game_details_short_data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import myapplication.android.pixelpal.databinding.RecyclerViewMainNewsItemBinding
import myapplication.android.pixelpal.ui.delegates.delegates.emptyItems.EmptyItemAdapter
import myapplication.android.pixelpal.ui.delegates.delegates.emptyItems.EmptyItemsModel
import myapplication.android.pixelpal.ui.delegates.main.AdapterDelegate
import myapplication.android.pixelpal.ui.delegates.main.DelegateItem
import myapplication.android.pixelpal.ui.games.games.recycler_view.GamesShortModel
import myapplication.android.pixelpal.ui.games.games.recycler_view.gridItem.GamesShortGridAdapter
import myapplication.android.pixelpal.ui.listener.LinearPaginationScrollListener

class GameDetailsShortDataDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewMainNewsItemBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as GameDetailsShortDataModel)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int,
        payloads: MutableList<Any>
    ) {
        (holder as ViewHolder).updateRecycler(
            payloads[0] as MutableList<GamesShortModel>,
            (item.content() as GameDetailsShortDataModel).items
        )
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is GameDetailsShortDataDelegateItem

    class ViewHolder(private val binding: RecyclerViewMainNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val adapter = GamesShortGridAdapter()
        var loading = false
        var lastPage = false

        fun bind(model: GameDetailsShortDataModel) {
            with(binding) {
                title.setShimmerText(model.title)
                actionAll.setOnClickListener { model.listener.onClick() }
            }
            initRecycler(model.items, model.emptyTitle)
            checkRecyclerEnd(model)
        }

        private fun checkRecyclerEnd(
            model: GameDetailsShortDataModel
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
            items: MutableList<GamesShortModel>,
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
            newItems: MutableList<GamesShortModel>,
            items: MutableList<GamesShortModel>
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