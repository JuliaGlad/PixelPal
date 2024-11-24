package myapplication.android.pixelpal.ui.delegates.delegates.publishers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import myapplication.android.pixelpal.databinding.RecyclerViewPublishersItemBinding
import myapplication.android.pixelpal.ui.delegates.main.AdapterDelegate
import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class PublisherDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewPublishersItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind(item.content() as PublisherModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is PublisherDelegateItem

    private class ViewHolder(val binding: RecyclerViewPublishersItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: PublisherModel){
            with(binding){
                title.text = model.title
                projects.text = "${model.projects}"

                Glide.with(itemView.context)
                    .load(model.background.toUri())
                    .into(image)

                item.setOnClickListener { model.clickListener.onClick() }
            }

        }
    }
}