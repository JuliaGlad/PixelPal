package myapplication.android.pixelpal.ui.delegates.delegates.info_box

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.recyclerview.widget.RecyclerView
import myapplication.android.pixelpal.databinding.RecyclerViewInfoBoxBinding
import myapplication.android.pixelpal.ui.delegates.main.AdapterDelegate
import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class InfoBoxDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewInfoBoxBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind(item.content() as InfoBoxModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is InfoBoxDelegateItem

    private class ViewHolder(private val binding: RecyclerViewInfoBoxBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: InfoBoxModel) {
            with(binding) {
                val margin = (root.layoutParams as MarginLayoutParams)
                margin.bottomMargin = (20 * itemView.resources.displayMetrics.density).toInt()
                text.setShimmerText(model.title)
                item.setOnClickListener { model.listener.onClick() }
            }
        }
    }
}