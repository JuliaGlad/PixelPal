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
import myapplication.android.pixelpal.app.App.Companion.app
import myapplication.android.pixelpal.app.App.Companion.appComponent
import myapplication.android.pixelpal.databinding.FragmentCreatorsBinding
import myapplication.android.pixelpal.ui.creators.model.creatores.CreatorUi
import myapplication.android.pixelpal.ui.creators.model.creatores.CreatorsUiList
import myapplication.android.pixelpal.ui.creators.model.publisher.PublisherUi
import myapplication.android.pixelpal.ui.creators.model.roles.RolesUi
import myapplication.android.pixelpal.ui.creators.mvi.CreatorsEffect
import myapplication.android.pixelpal.ui.creators.mvi.CreatorsIntent
import myapplication.android.pixelpal.ui.creators.mvi.CreatorsLocalDI
import myapplication.android.pixelpal.ui.creators.mvi.CreatorsPartialState
import myapplication.android.pixelpal.ui.creators.mvi.CreatorsState
import myapplication.android.pixelpal.ui.creators.mvi.CreatorsStoreFactory
import myapplication.android.pixelpal.ui.custom_view.flexBox.CreatorView
import myapplication.android.pixelpal.ui.custom_view.flexBox.FlexBoxLayout
import myapplication.android.pixelpal.ui.delegates.delegates.creators.CreatorsDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.creators.CreatorsDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.creators.CreatorsModel
import myapplication.android.pixelpal.ui.delegates.delegates.publishers.PublisherDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.publishers.PublisherDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.publishers.PublisherModel
import myapplication.android.pixelpal.ui.delegates.main.DelegateItem
import myapplication.android.pixelpal.ui.delegates.main.MainAdapter
import myapplication.android.pixelpal.ui.listener.ClickIntegerListener
import myapplication.android.pixelpal.ui.listener.ClickListener
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviBaseFragment
import myapplication.android.pixelpal.ui.mvi.MviStore
import java.util.stream.Collectors
import javax.inject.Inject

class CreatorsFragment :
    MviBaseFragment<
            CreatorsPartialState,
            CreatorsIntent,
            CreatorsState,
            CreatorsEffect>(R.layout.fragment_creators) {
    private val creatorsComponent by lazy {
        appComponent.creatorsComponent().create()
    }
    private val adapter = MainAdapter()
    private var isFirst = true
    private val viewModel: CreatorsViewModel by viewModels {
        CreatorsViewModel.Factory(
            creatorsComponent.creatorsViewModelFactory()
        )
    }

    private val roles: MutableList<RolesUi> = mutableListOf()
    private var _binding: FragmentCreatorsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var creatorsLocalDI: CreatorsLocalDI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        creatorsComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatorsBinding.inflate(layoutInflater)
        return binding.root
    }

    override val store: MviStore<CreatorsPartialState, CreatorsIntent, CreatorsState, CreatorsEffect>
            by viewModels {
                CreatorsStoreFactory(
                    creatorsLocalDI.reducer,
                    creatorsLocalDI.actor
                )
            }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        collectRoles()
        if (savedInstanceState == null) {
            store.sendIntent(CreatorsIntent.Init)
        }
        store.sendIntent(CreatorsIntent.GetRolesCreators(1))
    }

    override fun resolveEffect(effect: CreatorsEffect) {
        TODO("Handle effects")
    }

    override fun render(state: CreatorsState) {
        when (state.ui) {
            is LceState.Content -> {
                binding.loading.root.visibility = GONE

                initRecycler(state.ui.data)

                binding.loadingRecycler.root.visibility = GONE
            }

            is LceState.Error -> {
                binding.loading.root.visibility = GONE
                Log.i("Error creators", state.ui.throwable.message.toString())
            }

            LceState.Loading -> binding.loading.root.visibility = VISIBLE
        }
    }

    private fun initAdapter() {
        adapter.addDelegate(PublisherDelegate())
        adapter.addDelegate(CreatorsDelegate())
        binding.recyclerView.adapter = adapter
    }

    private fun collectRoles() {
        lifecycleScope.launch {
            viewModel.roles.collect { items ->
                items.forEach {
                    roles.add(it)
                    binding.flexBox.addCreators(it.title, it.id)
                }
                val roleUi = RolesUi(PUBLISHER_ID, getString(R.string.publishers))
                roles.add(roleUi)
                binding.flexBox.addCreators(roleUi.title, roleUi.id)

                val first = (binding.flexBox.children.first() as CreatorView)
                first.isChosen = true
                binding.title.setShimmerText(first.creator)
            }
        }
    }

    private fun initRecycler(creatorsUiList: CreatorsUiList) {
        if (creatorsUiList.creators != null) {
            initCreatorsRecycler(creatorsUiList.creators)
        } else if (creatorsUiList.publishers != null) {
            initPublishersRecycler(creatorsUiList.publishers)
        }
    }

    private fun initPublishersRecycler(publishers: List<PublisherUi>) {
        val publishersModel = mutableListOf<DelegateItem>()
        for (i in publishers) {
            with(i) {
                publishersModel.addPublisherItem(
                    publishers.indexOf(i),
                    id,
                    name,
                    gamesCount,
                    background
                )
            }
        }
        adapter.submitList(publishersModel)
        if (isFirst) isFirst = false
        else adapter.onCurrentListChanged(adapter.currentList, publishersModel)
    }

    private fun initCreatorsRecycler(creators: List<CreatorUi>) {
        val creatorsModel = mutableListOf<DelegateItem>()
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
        if (isFirst) isFirst = false
        else adapter.onCurrentListChanged(adapter.currentList, creatorsModel)
    }

    private fun MutableList<DelegateItem>.addCreatorItem(
        index: Int,
        creatorId: Long,
        name: String,
        roles: List<String>,
        famousProjects: Int,
        image: String
    ) {
        add(CreatorsDelegateItem(CreatorsModel(
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
        )))
    }

    private fun MutableList<DelegateItem>.addPublisherItem(
        index: Int,
        creatorId: Long,
        name: String,
        famousProjects: Int,
        image: String
    ) {
        add(PublisherDelegateItem(PublisherModel(
            index,
            creatorId,
            name,
            famousProjects,
            image,
            object : ClickListener {
                override fun onClick() {
                    //TODO("Open details screen")
                }
            }
        )))
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
                            if (it.isChosen) {
                                binding.title.setShimmerText(it.creator)
                            }

                        }
                        binding.loadingRecycler.root.visibility = VISIBLE
                        if (int != PUBLISHER_ID) {
                            store.sendIntent(CreatorsIntent.GetRolesCreators(int))
                        } else {
                            store.sendIntent(CreatorsIntent.GetPublishers)
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

    companion object {
        const val PUBLISHER_ID = 33
    }

}