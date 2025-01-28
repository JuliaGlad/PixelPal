package myapplication.android.pixelpal.ui.delegates.delegates.text_input

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import myapplication.android.pixelpal.databinding.RecyclerViewTextfieldItemBinding
import myapplication.android.pixelpal.ui.delegates.main.AdapterDelegate
import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class TextInputLayoutDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewTextfieldItemBinding.inflate(
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
        (holder as ViewHolder).bind((item.content() as TextInputLayoutModel))
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is TextInputLayoutDelegateItem

    class ViewHolder(private val binding: RecyclerViewTextfieldItemBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ){
            fun bind(model: TextInputLayoutModel){
                with(binding){
                    title.setShimmerText(model.title)
                    textInputEdit.hint = model.hint
                    textInputEdit.inputType = model.inputType
                    textInputEdit.addTextChangedListener(model.textWatcher)
                }
            }

    }
}