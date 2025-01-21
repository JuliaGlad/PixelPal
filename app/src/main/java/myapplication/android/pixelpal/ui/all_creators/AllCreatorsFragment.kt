package myapplication.android.pixelpal.ui.all_creators

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.app.App.Companion.appComponent
import myapplication.android.pixelpal.app.Constants
import myapplication.android.pixelpal.databinding.FragmentAllCreatorsBinding
import myapplication.android.pixelpal.ui.all_creators.mvi.AllCreatorsEffect
import myapplication.android.pixelpal.ui.all_creators.mvi.AllCreatorsIntent
import myapplication.android.pixelpal.ui.all_creators.mvi.AllCreatorsLocalDI
import myapplication.android.pixelpal.ui.all_creators.mvi.AllCreatorsPartialState
import myapplication.android.pixelpal.ui.all_creators.mvi.AllCreatorsState
import myapplication.android.pixelpal.ui.all_creators.mvi.AllCreatorsStoreFactory
import myapplication.android.pixelpal.ui.all_creators.recycler_view.AllCreatorsAdapter
import myapplication.android.pixelpal.ui.all_creators.recycler_view.AllCreatorsModel
import myapplication.android.pixelpal.ui.creators.model.creatores.CreatorUi
import myapplication.android.pixelpal.ui.creators.model.roles.RolesUi
import myapplication.android.pixelpal.ui.listener.ClickListener
import myapplication.android.pixelpal.ui.listener.LinearPaginationScrollListener
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviBaseFragment
import myapplication.android.pixelpal.ui.mvi.MviStore
import javax.inject.Inject

class AllCreatorsFragment : MviBaseFragment<
        AllCreatorsPartialState,
        AllCreatorsIntent,
        AllCreatorsState,
        AllCreatorsEffect>(R.layout.fragment_all_creators) {

    @Inject
    lateinit var localDI: AllCreatorsLocalDI

    private var loading = false
    private var lastPage = false

    private val gameId by lazy { activity?.intent?.getLongExtra(Constants.GAME_ID_ARG, 0L) }
    val recyclerItems = mutableListOf<AllCreatorsModel>()
    val adapter = AllCreatorsAdapter()

    private var _binding: FragmentAllCreatorsBinding? = null
    private val binding get() = _binding!!

    override val store: MviStore<AllCreatorsPartialState, AllCreatorsIntent, AllCreatorsState, AllCreatorsEffect>
            by viewModels { AllCreatorsStoreFactory(localDI.actor, localDI.reducer) }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.allCreatorsComponent().create().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllCreatorsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtonBack()
        if (savedInstanceState == null){
            store.sendIntent(AllCreatorsIntent.Init)
        }
        store.sendIntent(AllCreatorsIntent.GetCreators(gameId!!))
    }

    private fun initButtonBack() {
        binding.buttonBack.setOnClickListener { store.sendEffect(AllCreatorsEffect.NavigateBack) }
    }

    private fun updateRecycler(items: List<CreatorUi>){
        val models = getCreatorModels(items)
        val startPosition = adapter.currentList.size
        recyclerItems.addAll(models)
        adapter.notifyItemRangeInserted(startPosition, models.size)
    }

    override fun resolveEffect(effect: AllCreatorsEffect) {
        when(effect){
            AllCreatorsEffect.NavigateBack -> activity?.finish()
            is AllCreatorsEffect.OpenAllCreatorDetails ->{
                with(effect){
                    (activity as AllCreatorsActivity).openCreatorDetailsActivity(
                        creatorId, name, role, famousProjects, image
                    )
                }
            }
        }
    }

    override fun render(state: AllCreatorsState) {
        when(state.ui){
            is LceState.Content -> {
                binding.loading.root.visibility = GONE
                if (!lastPage) {
                    initRecycler(state.ui.data.items)
                } else {
                    updateRecycler(state.ui.data.items)
                }
            }
            is LceState.Error -> {
                binding.loading.root.visibility = GONE
                Log.e("AllCreatorsError", state.ui.throwable.message.toString())
            }
            LceState.Loading -> binding.loading.root.visibility = VISIBLE
        }
    }

    private fun initRecycler(items: List<CreatorUi>) {
        recyclerItems.addAll(getCreatorModels(items))
        binding.recyclerView.adapter = adapter
        adapter.submitList(recyclerItems)
        addEndListener()
    }

    private fun addEndListener() {
        with(binding.recyclerView) {
            addOnScrollListener(object :
                LinearPaginationScrollListener(layoutManager as LinearLayoutManager) {
                override fun isLastPage(): Boolean = lastPage

                override fun isLoading(): Boolean = loading

                override fun loadMoreItems() {
                    lastPage = true
                    loading = true
                    store.sendIntent(AllCreatorsIntent.GetCreators(gameId!!))
                }

            })
        }
    }

    private fun getCreatorModels(items: List<CreatorUi>): List<AllCreatorsModel> {
        val models = mutableListOf<AllCreatorsModel>()
        for (i in items){
            with(i) {
                models.add(
                    AllCreatorsModel(
                        items.indexOf(i),
                        name,
                        role,
                        famousProjects,
                        image,
                        object : ClickListener{
                            override fun onClick() {
                                store.sendEffect(
                                    AllCreatorsEffect.OpenAllCreatorDetails(
                                    creatorId,
                                    name,
                                    role.toStringArray(),
                                    famousProjects,
                                    image
                                ))
                            }
                        }
                    )
                )
            }
        }
        return models
    }

    fun List<RolesUi>.toStringArray(): Array<String?>{
        val array = arrayOfNulls<String>(size)
        for (i in this.indices){
            array[i] = get(i).title
        }
        return array
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}