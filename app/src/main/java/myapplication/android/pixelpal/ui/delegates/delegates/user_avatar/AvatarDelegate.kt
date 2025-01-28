package myapplication.android.pixelpal.ui.delegates.delegates.user_avatar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import myapplication.android.pixelpal.databinding.RecyclerViewUserAvatarBinding
import myapplication.android.pixelpal.ui.delegates.main.AdapterDelegate
import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class AvatarDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewUserAvatarBinding.inflate(
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
        (holder as ViewHolder).bind((item.content() as AvatarModel))
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is AvatarDelegateItem

    class ViewHolder(private val binding: RecyclerViewUserAvatarBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(model: AvatarModel) {
            with(binding) {
                model.uri?.let { image ->
                    Glide.with(itemView.context)
                        .setDefaultRequestOptions(RequestOptions.circleCropTransform())
                        .load(image.toUri())
                        .into(imageView)
                }
                if (model.clickListener != null){
                    imageView.setOnClickListener { model.clickListener.onClick() }
                }
            }
        }
    }
}