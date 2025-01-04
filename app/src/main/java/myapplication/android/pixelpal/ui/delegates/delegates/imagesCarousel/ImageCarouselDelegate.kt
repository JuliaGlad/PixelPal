package myapplication.android.pixelpal.ui.delegates.delegates.imagesCarousel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import myapplication.android.pixelpal.databinding.RecyclerViewImageCarouselBinding
import myapplication.android.pixelpal.ui.delegates.main.AdapterDelegate
import myapplication.android.pixelpal.ui.delegates.main.DelegateItem
import myapplication.android.pixelpal.ui.game_details.pageAdapter.CarouselPagerAdapter
import java.util.stream.Collectors

class ImageCarouselDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewImageCarouselBinding.inflate(
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
        (holder as ViewHolder).bind((item.content() as ImagesCarouselModel))
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is ImagesCarouselDelegateItem

    class ViewHolder(private val binding: RecyclerViewImageCarouselBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ImagesCarouselModel){
            with(binding){
                title.setShimmerText(model.title)
                val pagerAdapter = CarouselPagerAdapter(itemView.context,
                    model.images
                        .stream()
                        .map { it.toUri() }
                        .collect(Collectors.toList())
                )
                viewPager.adapter = pagerAdapter
            }
        }
    }
}