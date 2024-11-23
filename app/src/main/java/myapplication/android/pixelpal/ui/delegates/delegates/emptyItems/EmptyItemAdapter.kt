package myapplication.android.pixelpal.ui.delegates.delegates.emptyItems

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import myapplication.android.pixelpal.databinding.RecyclerViewNoItemsBinding

class EmptyItemAdapter : ListAdapter<EmptyItemsModel, RecyclerView.ViewHolder>(EmptyItemCallBack()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewNoItemsBinding.inflate(
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

    private class ViewHolder(private val binding: RecyclerViewNoItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: EmptyItemsModel){
            binding.title.setShimmerText(model.title)
        }
    }

}