package myapplication.android.pixelpal.ui.delegates.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import myapplication.android.pixelpal.databinding.RecyclerViewMainNewsItemBinding
import myapplication.android.pixelpal.ui.delegates.AdapterDelegate
import myapplication.android.pixelpal.ui.delegates.DelegateItem

class NewsItemDelegate : AdapterDelegate {

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
            binding.title
//            binding.title = model.title
//            binding.actionAll.setOnClickListener { model.listener.onClick() }
//            val adapter =
//                myapplication.android.pixelpal.ui.home.recycler_view.releases.ReleasesAdapter()
//            binding.recyclerView.adapter = adapter
        }
    }
}