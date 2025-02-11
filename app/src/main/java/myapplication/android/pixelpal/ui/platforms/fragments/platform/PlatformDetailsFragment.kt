package myapplication.android.pixelpal.ui.platforms.fragments.platform

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.app.App.Companion.appComponent
import myapplication.android.pixelpal.databinding.FragmentPlatformDetailsBinding
import myapplication.android.pixelpal.ui.listener.GridPaginationScrollListener
import myapplication.android.pixelpal.ui.main.MainActivity
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviBaseFragment
import myapplication.android.pixelpal.ui.platforms.PlatformPager
import myapplication.android.pixelpal.ui.platforms.fragments.platform.model.PlatformUiList
import myapplication.android.pixelpal.ui.platforms.fragments.platform.model.PlatformsUi
import myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi.PlatformEffect
import myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi.PlatformIntent
import myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi.PlatformLocalDI
import myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi.PlatformPartialState
import myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi.PlatformState
import myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi.PlatformStore
import myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi.PlatformStoreFactory
import myapplication.android.pixelpal.ui.platforms.fragments.platform.recycler_view.PlatformAdapter
import myapplication.android.pixelpal.ui.platforms.fragments.platform.recycler_view.PlatformModel
import javax.inject.Inject

@SuppressLint("NotifyDataSetChanged")
class PlatformDetailsFragment : MviBaseFragment<
        PlatformPartialState,
        PlatformIntent,
        PlatformState,
        PlatformEffect>(R.layout.fragment_platforms), PlatformPager {
    private val platformComponent by lazy {
        appComponent.platformComponent().create()
    }
    private val adapter = PlatformAdapter()
    private var _binding: FragmentPlatformDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var platformLocalDI: PlatformLocalDI
    private var previousQuery = ""
    private var query = ""
    private var loading = false
    private var needUpdate = false
    private val models = mutableListOf<PlatformModel>()

    override val store: PlatformStore by viewModels {
        PlatformStoreFactory(platformLocalDI.actor, platformLocalDI.reducer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        platformComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlatformDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun updateQuery(query: String) {
        previousQuery = this.query
        this.query = query
        if (isAdded) {
            if (previousQuery != query) {
                store.sendIntent(PlatformIntent.Init)
                models.clear()
                adapter.notifyDataSetChanged()
                previousQuery = query
            }
            needUpdate = true
            if (query.isEmpty()) store.sendIntent(PlatformIntent.GetPlatforms)
            else store.sendIntent(PlatformIntent.GetPlatformsByQuery(query))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            store.sendIntent(PlatformIntent.Init)
        }
        store.sendIntent(PlatformIntent.GetPlatforms)
    }

    override fun resolveEffect(effect: PlatformEffect) {
        when (effect) {
            is PlatformEffect.OpenPlatformDetails -> {
                with(effect) {
                    (activity as MainActivity).openPlatformDetailsActivity(
                        id,
                        name,
                        gamesCount,
                        startYear,
                        background
                    )
                }
            }
        }
    }

    override fun render(state: PlatformState) {
        when (state.ui) {
            is LceState.Content -> {
                binding.loadingRecycler.root.visibility = GONE
                if (!needUpdate) {
                    initRecycler(state.ui.data)
                } else if (query.isNotEmpty()){
                    updateQueryRecycler(state.ui.data.list)
                } else {
                    updateRecycler(state.ui.data.list)
                    if (models.size <= 4) store.sendIntent(PlatformIntent.GetPlatforms)
                }
            }
            is LceState.Error -> {
                binding.loadingRecycler.root.visibility = GONE
                Log.e("Platform error", state.ui.throwable.message.toString())
            }
            LceState.Loading -> binding.loadingRecycler.root.visibility = VISIBLE
        }
    }

    private fun updateQueryRecycler(platforms: List<PlatformsUi>){
        if (previousQuery != query){
            models.clear()
            adapter.notifyDataSetChanged()
            previousQuery = query
        }
        val startPosition = models.size
        val newItems = getPlatforms(platforms)
        models.addAll(newItems)
        adapter.notifyItemRangeInserted(startPosition, models.size)
        needUpdate = false
        loading = false
        binding.loadingRecycler.root.visibility = GONE
    }

    private fun updateRecycler(platforms: List<PlatformsUi>) {
        val newItems = getPlatforms(platforms)
        val position = models.size
        models.addAll(newItems)
        adapter.notifyItemRangeInserted(position, newItems.size)
        loading = false
        needUpdate = false
    }

    private fun getPlatforms(platforms: List<PlatformsUi>): MutableList<PlatformModel> {
        val newItems = mutableListOf<PlatformModel>()
        for (i in platforms) {
            with(i) {
                newItems.addPlatforms(
                    platforms.indexOf(i),
                    id,
                    name,
                    startYear,
                    gamesCount,
                    background
                )
            }
        }
        return newItems
    }

    private fun initRecycler(platforms: PlatformUiList) {
        binding.recyclerView.adapter = adapter
        val items = getPlatforms(platforms.list)
        models.addAll(items)
        adapter.submitList(models)
        addScrollRecyclerListener()
    }

    private fun MutableList<PlatformModel>.addPlatforms(
        id: Int,
        platformId: Long,
        name: String,
        startYear: Int?,
        gamesCount: Int,
        background: String,
    ) {
        add(
            PlatformModel(
                id,
                platformId,
                name,
                startYear,
                gamesCount,
                background
            ) {
                store.sendEffect(
                    PlatformEffect.OpenPlatformDetails(
                        id,
                        name,
                        gamesCount,
                        startYear,
                        background
                    )
                )
            })
    }

    private fun addScrollRecyclerListener() {
        with(binding.recyclerView) {
            addOnScrollListener(object : GridPaginationScrollListener(
                layoutManager as GridLayoutManager
            ) {
                override fun isLastPage(): Boolean = needUpdate

                override fun isLoading(): Boolean = loading

                override fun loadMoreItems() {
                    if (!isComputingLayout && scrollState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (query.isEmpty()) store.sendIntent(PlatformIntent.GetPlatforms)
                        else store.sendIntent(PlatformIntent.GetPlatformsByQuery(query))
                    }
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun getInstance() =
            PlatformDetailsFragment()
    }
}