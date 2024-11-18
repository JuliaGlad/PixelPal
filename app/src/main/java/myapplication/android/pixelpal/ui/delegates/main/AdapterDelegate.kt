package myapplication.android.pixelpal.ui.delegates.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface AdapterDelegate {
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DelegateItem, position: Int)

    fun isOfViewType(item: DelegateItem): Boolean

    fun onBindViewHolder( holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) = Unit

    fun getItem(position: Int) : DelegateItem? = null
}
