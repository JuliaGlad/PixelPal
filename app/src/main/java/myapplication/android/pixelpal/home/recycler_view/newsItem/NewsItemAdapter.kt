package myapplication.android.pixelpal.home.recycler_view.newsItem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import myapplication.android.pixelpal.databinding.RecyclerViewMainNewsItemBinding
import myapplication.android.pixelpal.home.recycler_view.releases.ReleasesAdapter
import myapplication.android.pixelpal.home.recycler_view.releases.ReleasesModel

class NewsItemAdapter : ListAdapter<NewsItemModel, RecyclerView.ViewHolder>(NewsItemCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewMainNewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(getItem(position))
    }

    private class ViewHolder(private val binding: RecyclerViewMainNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: NewsItemModel) {
            binding.title.text = model.title
            binding.actionAll.setOnClickListener { model.listener.onClick() }
            val adapter = ReleasesAdapter()
            binding.recyclerView.adapter = adapter
        }
    }
}