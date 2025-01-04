package myapplication.android.pixelpal.ui.game_details.recycler_view.creators

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import myapplication.android.pixelpal.databinding.RecyclerViewCreatorsItemBinding
import myapplication.android.pixelpal.ui.delegates.delegates.creators.CreatorsModel

class CreatorsAdapter :
    ListAdapter<CreatorsModel, RecyclerView.ViewHolder>(CreatorsItemCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewCreatorsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(getItem(position))
    }

    class ViewHolder(private val binding: RecyclerViewCreatorsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: CreatorsModel) {
            with(binding) {
                title.text = model.name
                var rolesText = ""
                for (i in model.roles) {
                    val newRole = "$rolesText $i"
                    rolesText = newRole
                }
                roles.text = rolesText
                projects.text = "${model.famousProjects}"

                if (model.image != null) {
                    Glide.with(itemView.context)
                        .load(model.image.toUri())
                        .override(100, 100)
                        .into(image)
                }
            }
        }
    }
}