package myapplication.android.pixelpal.ui.game_details.recycler_view.stores_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import myapplication.android.pixelpal.databinding.RecyclerViewStoreLinkBinding

class StoreLinkAdapter :
    ListAdapter<StoreLinkModels, RecyclerView.ViewHolder>(StoreLinkItemCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewStoreLinkBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(getItem(position))
    }

    class ViewHolder(private val binding: RecyclerViewStoreLinkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: StoreLinkModels) {
            with(binding) {
                text.text = model.link
                text.setOnClickListener { model.listener.onClick() }
            }
        }
    }
}