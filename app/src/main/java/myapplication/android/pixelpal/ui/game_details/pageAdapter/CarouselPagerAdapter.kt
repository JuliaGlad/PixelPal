package myapplication.android.pixelpal.ui.game_details.pageAdapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import myapplication.android.pixelpal.databinding.CarouselImageViewBinding


class CarouselPagerAdapter(private val context: Context, private val images: List<Uri>) :
    RecyclerView.Adapter<CarouselPagerAdapter.PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder =
        PagerViewHolder(
            CarouselImageViewBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(images[position])
    }

    class PagerViewHolder(private val binding: CarouselImageViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Uri) {
            Glide.with(itemView.context)
                .load(image)
                .into(binding.carouselAdapterImage)
        }
    }

}

//    private val layoutInflater =
//        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//
//    val count: Int = getCount()
//
//    override fun isViewFromObject(view: View, item: Any): Boolean {
//        return view == (item as ConstraintLayout)
//    }
//
//    override fun getCount(): Int = images.size
//
//    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        val itemView: View = layoutInflater.inflate(R.layout.carousel_image_view, container, false)
//
//        val imageView = itemView.findViewById<View>(R.id.carousel_adapter_image) as ImageView
//
//        Glide.with(context)
//            .load(images[position])
//            .into(imageView)
//
//        container.addView(itemView)
//
//        return itemView
//    }
//
//    override fun destroyItem(container: ViewGroup, position: Int, item: Any) {
//        super.destroyItem(container, position, item)
//        container.removeView(item as ConstraintLayout)
//    }

