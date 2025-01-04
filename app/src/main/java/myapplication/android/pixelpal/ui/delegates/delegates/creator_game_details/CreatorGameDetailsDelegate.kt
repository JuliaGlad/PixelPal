package myapplication.android.pixelpal.ui.delegates.delegates.creator_game_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import myapplication.android.pixelpal.databinding.RecyclerViewMainNewsItemBinding
import myapplication.android.pixelpal.ui.delegates.delegates.creators.CreatorsModel
import myapplication.android.pixelpal.ui.delegates.delegates.emptyItems.EmptyItemAdapter
import myapplication.android.pixelpal.ui.delegates.delegates.emptyItems.EmptyItemsModel
import myapplication.android.pixelpal.ui.delegates.main.AdapterDelegate
import myapplication.android.pixelpal.ui.delegates.main.DelegateItem
import myapplication.android.pixelpal.ui.game_details.recycler_view.creators.CreatorsAdapter
import myapplication.android.pixelpal.ui.listener.LinearPaginationScrollListener

class CreatorGameDetailsDelegate : AdapterDelegate {
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
        (holder as ViewHolder).bind((item.content() as CreatorGameDetailsModel))
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int,
        payloads: MutableList<Any>
    ) {
        (holder as ViewHolder).updateRecycler(
            payloads[0] as MutableList<CreatorsModel>,
            (item.content() as CreatorGameDetailsModel).items
        )
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is CreatorGameDetailsDelegateItem

    class ViewHolder(private val binding: RecyclerViewMainNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val adapter = CreatorsAdapter()
        var loading = false
        var lastPage = false

        fun bind(model: CreatorGameDetailsModel) {
            with(binding) {
                title.setShimmerText(model.title)
                actionAll.setOnClickListener { model.listener.onClick() }
            }
            initRecycler(model.items, model.emptyTitle)
            checkRecyclerEnd(model)
        }

        private fun checkRecyclerEnd(
            model: CreatorGameDetailsModel
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
            items: MutableList<CreatorsModel>,
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
            newItems: MutableList<CreatorsModel>,
            items: MutableList<CreatorsModel>
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