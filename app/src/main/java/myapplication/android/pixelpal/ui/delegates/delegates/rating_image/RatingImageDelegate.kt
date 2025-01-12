package myapplication.android.pixelpal.ui.delegates.delegates.rating_image

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import myapplication.android.pixelpal.databinding.RecyclerViewRatingImageBinding
import myapplication.android.pixelpal.ui.delegates.main.AdapterDelegate
import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class RatingImageDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewRatingImageBinding.inflate(
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
        (holder as ViewHolder).bind((item.content() as RatingImageModel))
    }

    override fun isOfViewType(item: DelegateItem): Boolean =
        item is RatingImageDelegateItem

    class ViewHolder(val binding: RecyclerViewRatingImageBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(model: RatingImageModel){
                with(binding){
                    Glide.with(itemView.context)
                        .load(model.image.toUri())
                        .into(image)

                    layoutRating.rating.text ="${model.rating}"
                }
            }
        }
}