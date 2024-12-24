package myapplication.android.pixelpal.ui.platforms.fragments.platform

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
import myapplication.android.pixelpal.ui.listener.ClickListener
import myapplication.android.pixelpal.ui.listener.GridPaginationScrollListener
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviBaseFragment
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

class PlatformDetailsFragment : MviBaseFragment<
        PlatformPartialState,
        PlatformIntent,
        PlatformState,
        PlatformEffect>(R.layout.fragment_platforms) {
    private val platformComponent by lazy {
        appComponent.platformComponent().create()
    }
    private val adapter = PlatformAdapter()
    private var _binding: FragmentPlatformDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var platformLocalDI: PlatformLocalDI

    private var loading = false
    private var lastPage = false
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            store.sendIntent(PlatformIntent.Init)
        }
        store.sendIntent(PlatformIntent.GetPlatforms)
    }

    override fun resolveEffect(effect: PlatformEffect) {
        TODO("handle effects")
    }

    override fun render(state: PlatformState) {
        when (state.ui) {
            is LceState.Content -> {
                binding.loadingRecycler.root.visibility = GONE
                if (!lastPage) {
                    initRecycler(state.ui.data)
                } else {
                    updateRecycler(state.ui.data.list)
                }
            }

            is LceState.Error -> {
                binding.loadingRecycler.root.visibility = GONE
                Log.e("Platform error", state.ui.throwable.message.toString())
            }

            LceState.Loading -> binding.loadingRecycler.root.visibility = VISIBLE
        }
    }

    private fun updateRecycler(platforms: List<PlatformsUi>) {
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
        val position = models.size
        models.addAll(newItems)
        adapter.notifyItemRangeInserted(position, newItems.size)
        loading = false
        lastPage = false
    }

    private fun initRecycler(platforms: PlatformUiList) {
        binding.recyclerView.adapter = adapter
        for (i in platforms.list) {
            with(i) {
                models.addPlatforms(
                    platforms.list.indexOf(i),
                    id,
                    name,
                    startYear,
                    gamesCount,
                    background
                )
            }
        }
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
    ){
        add(
            PlatformModel(
                id,
                platformId,
                name,
                startYear,
                gamesCount,
                background,
                object : ClickListener {
                    override fun onClick() {
                        TODO("open platform details fragment")
                    }
                }
            ))
    }

    private fun addScrollRecyclerListener() {
        with(binding.recyclerView) {
            addOnScrollListener(object : GridPaginationScrollListener(
                layoutManager as GridLayoutManager
            ) {
                override fun isLastPage(): Boolean = lastPage

                override fun isLoading(): Boolean = loading

                override fun loadMoreItems() {
                    if (!isComputingLayout && scrollState == RecyclerView.SCROLL_STATE_IDLE) {
                        store.sendIntent(PlatformIntent.GetPlatforms)
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