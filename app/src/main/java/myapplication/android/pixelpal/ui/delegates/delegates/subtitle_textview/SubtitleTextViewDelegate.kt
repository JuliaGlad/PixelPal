package myapplication.android.pixelpal.ui.delegates.delegates.subtitle_textview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import myapplication.android.pixelpal.databinding.RecyclerViewSubtitleTextViewBinding
import myapplication.android.pixelpal.ui.delegates.main.AdapterDelegate
import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class SubtitleTextViewDelegate: AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewSubtitleTextViewBinding.inflate(
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
        (holder as ViewHolder).bind((item.content() as SubtitleTextViewModel))
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is SubtitleTextViewDelegateItem

    class ViewHolder(private val binding: RecyclerViewSubtitleTextViewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(model: SubtitleTextViewModel){
            binding.title.setShimmerText(model.text)
        }
    }
}