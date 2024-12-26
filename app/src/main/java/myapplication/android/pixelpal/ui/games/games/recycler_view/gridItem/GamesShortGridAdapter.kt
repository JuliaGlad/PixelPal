package myapplication.android.pixelpal.ui.games.games.recycler_view.gridItem

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import myapplication.android.pixelpal.databinding.RecyclerViewGridGameItemBinding
import myapplication.android.pixelpal.ui.games.games.recycler_view.GamesShortItemCallBack
import myapplication.android.pixelpal.ui.games.games.recycler_view.GamesShortModel

class GamesShortGridAdapter :
    ListAdapter<GamesShortModel, RecyclerView.ViewHolder>(GamesShortItemCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewGridGameItemBinding.inflate(
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

    private class ViewHolder(private val binding: RecyclerViewGridGameItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(model: GamesShortModel) {
            with(binding) {
                if (model.rating != null) rating.text = "${model.rating}"
                else rating.text = "???"

                playtime.text = "${model.playtime}h"
                creationDate.text = model.releaseDate
                title.text = model.name

                Glide.with(itemView.context)
                    .load(model.image.toUri())
                    .override(200, 200)
                    .into(image)
            }
        }
    }
}