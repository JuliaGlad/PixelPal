package myapplication.android.pixelpal.ui.delegates.delegates.button_with_icon_warning

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import myapplication.android.pixelpal.databinding.RecyclerViewButtonWithIconBinding
import myapplication.android.pixelpal.databinding.RecyclerViewWarningButtonWithIconBinding
import myapplication.android.pixelpal.ui.delegates.main.AdapterDelegate
import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class ButtonWithIconWarningDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewWarningButtonWithIconBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as ButtonWithIconWarningModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is ButtonWithIconWarningDelegateItem

    class ViewHolder(val binding: RecyclerViewWarningButtonWithIconBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ButtonWithIconWarningModel){
            with(binding) {
                description.setShimmerText(model.title)
                iconOutline.setIcon(ResourcesCompat.getDrawable(itemView.resources, model.icon, itemView.context.theme)!!)
                binding.item.setOnClickListener { model.listener.onClick() }
            }
        }
    }
}