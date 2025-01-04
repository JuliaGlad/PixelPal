package myapplication.android.pixelpal.ui.delegates.delegates.title_details_text_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import myapplication.android.pixelpal.databinding.RecyclerViewTitleDetailsTextViewBinding
import myapplication.android.pixelpal.ui.delegates.main.AdapterDelegate
import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class TitleDetailsTextViewDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewTitleDetailsTextViewBinding.inflate(
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
        (holder as ViewHolder).bind((item.content() as TitleDetailsTextViewModel))
    }

    override fun isOfViewType(item: DelegateItem): Boolean =
        item is TitleDetailsTextViewDelegateItem

    class ViewHolder(private val binding: RecyclerViewTitleDetailsTextViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: TitleDetailsTextViewModel) {
            with(binding) {
                title.text = model.title
                details.text = model.details
            }
        }
    }
}