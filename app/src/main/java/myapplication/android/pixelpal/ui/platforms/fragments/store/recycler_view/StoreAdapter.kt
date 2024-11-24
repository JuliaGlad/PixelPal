package myapplication.android.pixelpal.ui.platforms.fragments.store.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import myapplication.android.pixelpal.databinding.RecyclerViewStoresItemBinding

class StoreAdapter : ListAdapter<StoreModel, RecyclerView.ViewHolder>(StoreItemCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewStoresItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(getItem(position))
    }

    private class ViewHolder(private val binding: RecyclerViewStoresItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: StoreModel) {
            binding.title.text = model.title
            binding.domain.text = model.domain
            binding.projects.text = "${model.projects}"

            binding.item.setOnClickListener { model.listener.onClick() }

            Glide.with(itemView.context)
                .load(model.background.toUri())
                .into(binding.image)
        }
    }
}
