package myapplication.android.pixelpal.ui.creators

import android.annotation.SuppressLint
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
import androidx.core.view.isEmpty
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.databinding.FragmentCreatorsBinding
import myapplication.android.pixelpal.di.DaggerAppComponent
import myapplication.android.pixelpal.ui.creators.di.DaggerCreatorsComponent
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
import myapplication.android.pixelpal.ui.listener.GridPaginationScrollListener
import myapplication.android.pixelpal.ui.main.MainActivity
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviBaseFragment
import myapplication.android.pixelpal.ui.mvi.MviStore
import java.util.stream.Collectors
import javax.inject.Inject

@SuppressLint("NotifyDataSetChanged")
@OptIn(ExperimentalCoroutinesApi::class)
class CreatorsFragment :
    MviBaseFragment<
            CreatorsPartialState,
            CreatorsIntent,
            CreatorsState,
            CreatorsEffect>(R.layout.fragment_creators) {
    private val creatorsComponent by lazy {
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerCreatorsComponent.factory().create(appComponent)
    }
    private val adapter = MainAdapter()
    private var isFirst = true
    private val viewModel: CreatorsViewModel by viewModels {
        CreatorsViewModel.Factory(
            creatorsComponent.creatorsViewModelFactory()
        )
    }
    private var chosenRole = 1
    private var needUpdate = false
    private var loading = false
    private var lastPage = false
    private val items: MutableList<DelegateItem> = mutableListOf()
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
        setupObserves()
        if (savedInstanceState == null) {
            binding.loading.root.visibility = VISIBLE
            store.sendIntent(CreatorsIntent.Init)
        }
        store.sendIntent(CreatorsIntent.GetRolesCreators(chosenRole))
    }

    private fun setupObserves() {
        lifecycleScope.launch {
            viewModel.searchCreatorsResult
                .collect { result ->
                    updateRecycler(result)
                }
        }

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

    override fun resolveEffect(effect: CreatorsEffect) {
        when (effect) {
            is CreatorsEffect.OpenCreatorDetailsScreen -> {
                with(effect) {
                    (activity as MainActivity).openCreatorDetailsActivity(
                        creatorId, name, role, famousProjects, image
                    )
                }
            }

            is CreatorsEffect.OpenPublisherDetailsScreen -> {
                with(effect) {
                    (activity as MainActivity).openPublisherDetailsActivity(
                        id, name, gameCount, background
                    )
                }
            }
        }
    }

    override fun render(state: CreatorsState) {
        when (state.ui) {
            is LceState.Content -> {
                binding.loading.root.visibility = GONE

                if (!needUpdate) {
                    initRecycler(state.ui.data)
                    addSearchTextChangeListener()
                } else {
                    updateRecycler(state.ui.data)
                }
                binding.loadingRecycler.root.visibility = GONE
            }

            is LceState.Error -> {
                binding.loading.root.visibility = GONE
                binding.loadingRecycler.root.visibility = GONE
                Log.i("Error creators", state.ui.throwable.message.toString())
            }

            LceState.Loading -> binding.loading.root.visibility = VISIBLE
        }
    }

    private fun addSearchTextChangeListener() {
        binding.searchItem.messageEditText.addTextChangedListener { text ->
            if (text != null) {
                if (text.isNotEmpty()) viewModel.searchQueryPublisher.tryEmit(text.toString())
                else {
                    updateListItemsFromInit()
                }
            }
        }
    }

    private fun updateListItemsFromInit() {
        store.sendIntent(CreatorsIntent.Init)
        items.clear()
        adapter.notifyDataSetChanged()
        needUpdate = true
        sendGetMoreIntent()
    }

    private fun updateRecycler(creatorsUiList: CreatorsUiList?) {
        if (!viewModel.isQuerySame.value) {
            items.clear()
            adapter.notifyDataSetChanged()
        }
        if (creatorsUiList != null) {
            val startPosition = items.size
            if (chosenRole != PUBLISHER_ID && creatorsUiList.creators != null) {
                if (creatorsUiList.creators.isNotEmpty()) {
                    updateCreatorsRecycler(startPosition, creatorsUiList.creators)
                    if (items.size <= 4) {
                        if (binding.searchItem.messageInputLayout.isEmpty()) {
                            store.sendIntent(CreatorsIntent.GetRolesCreators(chosenRole))
                            changeVariableValue(true)
                        } else {
                            viewModel.searchQueryPublisher.tryEmit(binding.searchItem.messageEditText.text.toString())
                        }
                        changeVariableValue(true)
                    }
                }
            } else if (creatorsUiList.publishers != null) {
                updatePublisherRecycler(startPosition, creatorsUiList.publishers)
                if (items.size <= 4) {
                    if (binding.searchItem.messageInputLayout.isEmpty()) {
                        store.sendIntent(CreatorsIntent.GetPublishers)
                        changeVariableValue(true)
                    } else {
                        viewModel.searchQueryPublisher.tryEmit(binding.searchItem.messageEditText.text.toString())
                    }
                    changeVariableValue(true)
                }
            }
            changeVariableValue(false)
        }
    }

    private fun changeVariableValue(value: Boolean) {
        needUpdate = value
        loading = value
        lastPage = value
    }

    private fun updatePublisherRecycler(startPosition: Int, publishers: List<PublisherUi>) {
        for (i in publishers) {
            items.addPublisherItem(
                publishers.indexOf(i),
                i.id,
                i.name,
                i.gamesCount,
                i.background
            )
        }
        adapter.notifyItemRangeInserted(startPosition, publishers.size)
        if (items.size <= 4) {
            store.sendIntent(CreatorsIntent.GetPublishers)
            changeVariableValue(true)
        }
    }

    private fun updateCreatorsRecycler(startPosition: Int, creators: List<CreatorUi>) {
        for (i in creators) {
            val rolesStr = i.role
                .stream()
                .map { it.title }
                .collect(Collectors.toList())

            items.addCreatorItem(
                creators.indexOf(i),
                i.creatorId,
                i.name,
                rolesStr,
                i.famousProjects,
                i.image
            )
        }
        adapter.notifyItemRangeInserted(startPosition, creators.size)
        binding.emptyQuery.root.visibility = GONE
    }

    private fun initAdapter() {
        adapter.addDelegate(PublisherDelegate())
        adapter.addDelegate(CreatorsDelegate())
        binding.recyclerView.adapter = adapter
    }

    private fun initRecycler(creatorsUiList: CreatorsUiList) {
        binding.emptyQuery.root.visibility = GONE
        if (creatorsUiList.creators != null) {
            initCreatorsRecycler(creatorsUiList.creators)
        } else if (creatorsUiList.publishers != null) {
            initPublishersRecycler(creatorsUiList.publishers)
        }
        addScrollRecyclerListener()
    }

    private fun addScrollRecyclerListener() {
        binding.recyclerView.addOnScrollListener(object : GridPaginationScrollListener(
            binding.recyclerView.layoutManager as GridLayoutManager
        ) {
            override fun isLastPage(): Boolean = lastPage

            override fun isLoading(): Boolean = loading

            override fun loadMoreItems() {
                if (!binding.recyclerView.isComputingLayout) {
                    changeVariableValue(true)
                    sendGetMoreIntent()
                }
            }
        })
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
        if (items.size <= 4) {
            store.sendIntent(CreatorsIntent.GetPublishers)
            changeVariableValue(true)
        }
    }

    private fun initCreatorsRecycler(creators: List<CreatorUi>) {
        for (i in creators) {
            val rolesStr = i.role
                .stream()
                .map { it.title }
                .collect(Collectors.toList())

            items.addCreatorItem(
                creators.indexOf(i),
                i.creatorId,
                i.name,
                rolesStr,
                i.famousProjects,
                i.image
            )
        }
        adapter.submitList(items)
        if (isFirst) isFirst = false
        else adapter.onCurrentListChanged(adapter.currentList, items)
        if (items.size <= 4) {
            store.sendIntent(CreatorsIntent.GetRolesCreators(chosenRole))
            changeVariableValue(true)
        }
    }

    private fun MutableList<DelegateItem>.addCreatorItem(
        index: Int,
        creatorId: Long,
        name: String,
        roles: List<String>,
        famousProjects: Int,
        image: String?
    ) {
        add(
            CreatorsDelegateItem(CreatorsModel(
                index,
                creatorId,
                name,
                famousProjects,
                roles,
                image
            ) {
                val rolesArray = arrayOfNulls<String>(roles.size)
                for ((roleIndex, i) in roles.withIndex()) {
                    rolesArray[roleIndex] = i
                }
                store.sendEffect(
                    CreatorsEffect.OpenCreatorDetailsScreen(
                        creatorId,
                        name,
                        rolesArray,
                        famousProjects,
                        image
                    )
                )
            })
        )
    }

    private fun MutableList<DelegateItem>.addPublisherItem(
        index: Int,
        creatorId: Long,
        name: String,
        famousProjects: Int,
        image: String?
    ) {
        add(
            PublisherDelegateItem(PublisherModel(
                index,
                creatorId,
                name,
                famousProjects,
                image
            ) {
                store.sendEffect(
                    CreatorsEffect.OpenPublisherDetailsScreen(
                        creatorId,
                        name,
                        famousProjects,
                        image
                    )
                )
            })
        )
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
                        onFlexBoxItemClicked(int)
                    }
                }
            onViewClicked()
        }
        addView(view, childCount, params)
    }

    private fun onFlexBoxItemClicked(int: Int) {
        binding.flexBox.forEach {
            (it as CreatorView).isChosen = it.viewId == int
            if (it.isChosen) {
                chosenRole = int
                viewModel.setChosenRole(chosenRole)
                binding.title.setShimmerText(it.creator)
            }
        }
        updateListItemsFromInit()
    }

    private fun sendGetMoreIntent() {
        val isEmpty = binding.searchItem.messageEditText.text?.isEmpty()
        if (chosenRole != PUBLISHER_ID) {
            if (isEmpty == true) store.sendIntent(CreatorsIntent.GetRolesCreators(chosenRole))
            else viewModel.searchQueryPublisher.tryEmit(binding.searchItem.messageEditText.text.toString())
        } else {
            if (isEmpty == true) store.sendIntent(CreatorsIntent.GetPublishers)
            else viewModel.searchQueryPublisher.tryEmit(binding.searchItem.messageEditText.text.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val PUBLISHER_ID = 33
    }

}