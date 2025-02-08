package myapplication.android.pixelpal.ui.games.games.recycler_view.one_item

import android.app.ActionBar.LayoutParams
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import myapplication.android.pixelpal.databinding.RecyclerViewGamesOneItemBinding
import myapplication.android.pixelpal.ui.games.games.recycler_view.GamesShortItemCallBack
import myapplication.android.pixelpal.ui.games.games.recycler_view.GamesShortModel

class GamesOneItemAdapter: ListAdapter<GamesShortModel, RecyclerView.ViewHolder>(
    GamesShortItemCallBack()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewGamesOneItemBinding.inflate(
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

    private class ViewHolder(private val binding:RecyclerViewGamesOneItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: GamesShortModel) {
            with(binding) {

                if (model.rating != null) layoutRating.rating.text = "${model.rating}"
                else layoutRating.root.visibility = GONE

                playtime.text = "${model.playtime}"
                creationDate.text = model.releaseDate
                title.text = model.name

                if (model.image != null) {
                    Glide.with(itemView.context)
                        .load(model.image.toUri())
                        .override(LayoutParams.MATCH_PARENT)
                        .into(image)
                }
                item.setOnClickListener { model.listener.onClick() }
            }
        }
    }
}