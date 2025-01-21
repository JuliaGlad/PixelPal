package myapplication.android.pixelpal.ui.all_creators.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import myapplication.android.pixelpal.databinding.RecyclerViewAllCreatorItemBinding

class AllCreatorsAdapter :
    ListAdapter<AllCreatorsModel, RecyclerView.ViewHolder>(AllCreatorsItemCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewAllCreatorItemBinding.inflate(
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

    class ViewHolder(private val binding: RecyclerViewAllCreatorItemBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(model: AllCreatorsModel){
                with(binding) {
                    title.text = model.name
                    projects.text = "${model.famousProjects}"
                    var rolesStr= ""
                    for (i in model.roles) {
                        rolesStr = "$rolesStr, $i"
                    }
                    roles.text = rolesStr
                    model.image?.let {
                        Glide.with(itemView.context)
                            .load(it.toUri())
                            .into(binding.image)
                    }
                }
            }
        }
}