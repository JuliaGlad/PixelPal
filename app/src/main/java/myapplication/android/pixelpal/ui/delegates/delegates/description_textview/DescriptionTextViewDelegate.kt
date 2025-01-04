package myapplication.android.pixelpal.ui.delegates.delegates.description_textview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import myapplication.android.pixelpal.databinding.RecyclerViewDescriptionTextViewBinding
import myapplication.android.pixelpal.ui.delegates.main.AdapterDelegate
import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class DescriptionTextViewDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewDescriptionTextViewBinding.inflate(
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
        (holder as ViewHolder).bind((item.content() as DescriptionTextViewModel))
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is DescriptionTextViewDelegateItem

    class ViewHolder(private val binding: RecyclerViewDescriptionTextViewBinding): RecyclerView.ViewHolder(
        binding.root
    ){
        fun bind(model: DescriptionTextViewModel){
            binding.text.text = model.text
        }
    }
}