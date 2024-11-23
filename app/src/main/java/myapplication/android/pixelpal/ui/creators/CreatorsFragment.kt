package myapplication.android.pixelpal.ui.creators

import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.databinding.FragmentCreatorsBinding
import myapplication.android.pixelpal.di.DiContainer
import myapplication.android.pixelpal.ui.creators.model.creatores.CreatorsUi
import myapplication.android.pixelpal.ui.creators.model.roles.RolesUi
import myapplication.android.pixelpal.ui.creators.mvi.CreatorsEffect
import myapplication.android.pixelpal.ui.creators.mvi.CreatorsIntent
import myapplication.android.pixelpal.ui.creators.mvi.CreatorsPartialState
import myapplication.android.pixelpal.ui.creators.mvi.CreatorsState
import myapplication.android.pixelpal.ui.custom_view.flexBox.CreatorView
import myapplication.android.pixelpal.ui.custom_view.flexBox.FlexBoxLayout
import myapplication.android.pixelpal.ui.delegates.delegates.creators.CreatorsAdapter
import myapplication.android.pixelpal.ui.delegates.delegates.creators.CreatorsModel
import myapplication.android.pixelpal.ui.listener.ClickIntegerListener
import myapplication.android.pixelpal.ui.listener.ClickListener
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviBaseFragment
import myapplication.android.pixelpal.ui.mvi.MviStore
import java.util.stream.Collectors


class CreatorsFragment :
    MviBaseFragment<
            CreatorsPartialState,
            CreatorsIntent,
            CreatorsState,
            CreatorsEffect>(R.layout.fragment_creators) {
    private val adapter = CreatorsAdapter()
    private val viewModel: CreatorsViewModel by viewModels()
    private val roles: MutableList<RolesUi> = mutableListOf()
    private var _binding: FragmentCreatorsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatorsBinding.inflate(layoutInflater)
        return binding.root
    }

    override val store: MviStore<CreatorsPartialState, CreatorsIntent, CreatorsState, CreatorsEffect>
            by viewModels { DiContainer.creatorsStoreFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectRoles()
        if (savedInstanceState == null) {
            store.sendIntent(CreatorsIntent.Init)
        }
        store.sendIntent(CreatorsIntent.GetRolesCreators(2))
    }

    override fun resolveEffect(effect: CreatorsEffect) {
        TODO("Handle effects")
    }

    override fun render(state: CreatorsState) {
        when (state.ui) {
            is LceState.Content -> {
                binding.loading.root.visibility = GONE
                initRecycler(state.ui.data)
            }

            is LceState.Error -> {
                binding.loading.root.visibility = GONE
                Log.i("Error creators", state.ui.throwable.message.toString())
            }

            LceState.Loading -> binding.loading.root.visibility = VISIBLE
        }
    }

    private fun collectRoles() {
        lifecycleScope.launch {
            viewModel.roles.collect { items ->
                items.forEach {
                    roles.add(it)
                    binding.flexBox.addCreators(it.title, items.indexOf(it))
                }
                val first = (binding.flexBox.children.first() as CreatorView)
                first.isChosen = true
                binding.title.setShimmerText(first.creator)
            }
        }
    }

    private fun initRecycler(creators: List<CreatorsUi>) {
        binding.recyclerView.adapter = adapter
        val creatorsModel = mutableListOf<CreatorsModel>()
        for (i in creators) {
            val rolesStr = i.role
                .stream()
                .map { it.title }
                .collect(Collectors.toList())

            creatorsModel.addCreatorItem(
                creators.indexOf(i),
                i.id,
                i.name,
                rolesStr,
                i.famousProjects,
                i.image
            )
        }
        adapter.submitList(creatorsModel)
    }

    private fun MutableList<CreatorsModel>.addCreatorItem(
        index: Int,
        creatorId: Long,
        name: String,
        roles: List<String>,
        famousProjects: Int,
        image: String
    ) {
        add(CreatorsModel(
            index,
            creatorId,
            name,
            famousProjects,
            roles,
            image,
            object : ClickListener {
                override fun onClick() {
                    //TODO("Open details screen")
                }
            }
        ))
    }

    private fun FlexBoxLayout.addCreators(title: String, id: Int) {
        val view = CreatorView(ContextThemeWrapper(requireContext(), R.style.CreatorItemStyle))
        val params = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        with(view) {
            viewId = id
            creator = title
            background =
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.creator_bg_unselected,
                    context.theme
                )
            foreground =
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.round_ripple,
                    context.theme
                )
            clickListener =
                object : ClickIntegerListener {
                    override fun onClick(int: Int) {
                        binding.flexBox.forEach {
                            (it as CreatorView).isChosen = it.viewId == int
                            if (it.isChosen) binding.title.setShimmerText(it.creator)
                        }
                    }
                }
            onViewClicked()
        }
        addView(view, childCount, params)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}