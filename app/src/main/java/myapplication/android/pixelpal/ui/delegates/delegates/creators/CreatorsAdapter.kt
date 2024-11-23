package myapplication.android.pixelpal.ui.delegates.delegates.creators

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import myapplication.android.pixelpal.databinding.RecyclerViewCreatorsItemBinding

class CreatorsAdapter :
    ListAdapter<CreatorsModel, RecyclerView.ViewHolder>(CreatorsItemCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewCreatorsItemBinding.inflate(
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

    private class ViewHolder(val binding: RecyclerViewCreatorsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: CreatorsModel) {
            binding.title.text = model.name
            var roles= ""
            for (i in model.roles){
                val newRole = "$roles, $i"
                roles = newRole
            }
            binding.roles.text = roles
            binding.projects.text = "${model.famousProjects}"
            Glide.with(itemView.context)
                .load(model.image.toUri())
                .into(binding.image)
        }
    }
}