package myapplication.android.pixelpal.ui.delegates.delegates.title_textview

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import myapplication.android.pixelpal.databinding.RecyclerViewTitleTextViewBinding
import myapplication.android.pixelpal.ui.delegates.main.AdapterDelegate
import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class TitleTextViewDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewTitleTextViewBinding.inflate(
                LayoutInflater.from(
                    parent.context,
                ), parent, false
            )
        )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind((item.content() as TitleTextViewModel))
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is TitleTextViewDelegateItem

    class ViewHolder(private val binding: RecyclerViewTitleTextViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: TitleTextViewModel) {
            with(binding) {
                if (model.gravity != Gravity.START) title.gravity = model.gravity
                title.setShimmerText(model.text)
            }
        }
    }
}