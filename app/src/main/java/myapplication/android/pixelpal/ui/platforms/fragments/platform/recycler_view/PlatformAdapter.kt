package myapplication.android.pixelpal.ui.platforms.fragments.platform.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.databinding.RecyclerViewPlatformItemBinding

class PlatformAdapter :
    ListAdapter<PlatformModel, RecyclerView.ViewHolder>(PlatformItemCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewPlatformItemBinding.inflate(
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

    private class ViewHolder(private val binding: RecyclerViewPlatformItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: PlatformModel) {
            with(binding) {
                title.text = model.title
                projects.text = "${model.projects}"
                yearStart.text = if (model.startYear != null) "${model.startYear}"
                else itemView.context.getString(R.string.unknown)

                Glide.with(itemView.context)
                    .load(model.background.toUri())
                    .into(image)

                item.setOnClickListener { model.listener.onClick() }
            }
        }
    }
}