package myapplication.android.pixelpal.ui.delegates.delegates.image_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import myapplication.android.pixelpal.databinding.RecyclerViewImageViewBinding
import myapplication.android.pixelpal.ui.delegates.main.AdapterDelegate
import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class ImageViewDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewImageViewBinding.inflate(
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
        (holder as ViewHolder).bind((item.content()) as ImageViewModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is ImageViewDelegateItem

    class ViewHolder(private val binding: RecyclerViewImageViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ImageViewModel) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(model.uri.toUri())
                    .into(image)
            }
        }
    }
}