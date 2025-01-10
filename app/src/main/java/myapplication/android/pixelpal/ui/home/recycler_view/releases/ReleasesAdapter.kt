package myapplication.android.pixelpal.ui.home.recycler_view.releases

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import myapplication.android.pixelpal.databinding.RecyclerViewNewReleasesItemBinding

class ReleasesAdapter :
    ListAdapter<ReleasesModel, RecyclerView.ViewHolder>(
        ReleasesItemCallBack()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewNewReleasesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(getItem(position))
    }

    private class ViewHolder(private val binding: RecyclerViewNewReleasesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ReleasesModel) {
            with(binding) {
                releaseDate.text = model.releaseDate
                title.text = model.title
                genres.text = model.genres
                if (model.uri.isNotEmpty()) {
                    image.setImageUri(model.uri.toUri())
                }

                item.setOnClickListener { model.listener.onClick() }
            }
        }
    }
}