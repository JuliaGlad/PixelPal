package myapplication.android.pixelpal.ui.delegates.main

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class MainAdapter : ListAdapter<DelegateItem, RecyclerView.ViewHolder>(
    DelegateItemCallBack()
) {

    private val delegates: ArrayList<AdapterDelegate> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        delegates[viewType].onCreateViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegates[getItemViewType(position)].onBindViewHolder(holder, getItem(position), position)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()){
            onBindViewHolder(holder, position)
        } else {
            delegates[getItemViewType(position)].onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemViewType(position: Int): Int {
        for (i in delegates.indices) {
            if (delegates[i].isOfViewType(currentList[position])) {
                return i
            }
        }
        return -1
    }

    fun addDelegate(delegate : AdapterDelegate){
        delegates.add(delegate)
    }
}