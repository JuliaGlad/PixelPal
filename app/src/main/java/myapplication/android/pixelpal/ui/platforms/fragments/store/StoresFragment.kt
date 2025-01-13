package myapplication.android.pixelpal.ui.platforms.fragments.store

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
import myapplication.android.pixelpal.databinding.FragmnetStoreBinding
import myapplication.android.pixelpal.ui.listener.ClickListener
import myapplication.android.pixelpal.ui.listener.GridPaginationScrollListener
import myapplication.android.pixelpal.ui.main.MainActivity
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviBaseFragment
import myapplication.android.pixelpal.ui.platforms.fragments.store.model.StoreUi
import myapplication.android.pixelpal.ui.platforms.fragments.store.model.StoresUiList
import myapplication.android.pixelpal.ui.platforms.fragments.store.mvi.StoresEffect
import myapplication.android.pixelpal.ui.platforms.fragments.store.mvi.StoresIntent
import myapplication.android.pixelpal.ui.platforms.fragments.store.mvi.StoresLocalDI
import myapplication.android.pixelpal.ui.platforms.fragments.store.mvi.StoresPartialState
import myapplication.android.pixelpal.ui.platforms.fragments.store.mvi.StoresState
import myapplication.android.pixelpal.ui.platforms.fragments.store.mvi.StoresStore
import myapplication.android.pixelpal.ui.platforms.fragments.store.mvi.StoresStoreFactory
import myapplication.android.pixelpal.ui.platforms.fragments.store.recycler_view.StoreAdapter
import myapplication.android.pixelpal.ui.platforms.fragments.store.recycler_view.StoreModel
import javax.inject.Inject

class StoresFragment :
    MviBaseFragment<StoresPartialState, StoresIntent, StoresState, StoresEffect>(R.layout.fragmnet_store) {
    private val storesComponent by lazy {
        appComponent.storesComponent().create()
    }
    private val adapter = StoreAdapter()
    private val models = mutableListOf<StoreModel>()
    private var _binding: FragmnetStoreBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var storesLocalDI: StoresLocalDI

    var loading = false
    var lastPage = false

    override val store: StoresStore by viewModels {
        StoresStoreFactory(
            storesLocalDI.reducer, storesLocalDI.actor
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storesComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmnetStoreBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            store.sendIntent(StoresIntent.Init)
        }
        store.sendIntent(StoresIntent.GetStores)
    }

    override fun resolveEffect(effect: StoresEffect) {
        when (effect) {
            is StoresEffect.OpenStoresDetailsScreen -> {
                with(effect) {
                    (activity as MainActivity).openStoreDetailsActivity(
                        id, name, image, domain, projects
                    )
                }
            }
        }
    }

    override fun render(state: StoresState) {
        when (state.ui) {
            is LceState.Content -> {
                binding.loadingRecycler.root.visibility = GONE
                if (!lastPage) {
                    initRecycler(state.ui.data)
                } else {
                    updateRecycler(state.ui.data.stores)
                }
            }

            is LceState.Error -> {
                binding.loadingRecycler.root.visibility = GONE
                Log.e("Stores error", state.ui.throwable.message.toString())
            }

            LceState.Loading -> binding.loadingRecycler.root.visibility = VISIBLE
        }
    }

    private fun updateRecycler(stores: List<StoreUi>) {
        val newItems = mutableListOf<StoreModel>()
        for (i in stores) {
            with(i) {
                newItems.add(
                    stores.indexOf(i), id, name, domain, projects, image
                )
            }
        }
        val positionStart = models.size
        models.addAll(newItems)
        adapter.notifyItemRangeInserted(positionStart, newItems.size)
        loading = false
        lastPage = false
    }

    private fun initRecycler(stores: StoresUiList) {
        binding.recyclerView.adapter = adapter
        for (i in stores.stores) {
            models.add(stores.stores.indexOf(i), i.id, i.name, i.domain, i.projects, i.image)
        }
        adapter.submitList(models)
        addScrollRecyclerListener()
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
                        lastPage = true
                        loading = true
                        store.sendIntent(StoresIntent.GetStores)
                    }
                }

            })
        }
    }

    private fun MutableList<StoreModel>.add(
        id: Int, storeId: Int, name: String, domain: String?, projects: Int?, image: String
    ) {
        add(StoreModel(id, storeId, name, domain, projects, image, object : ClickListener {
            override fun onClick() {
                store.sendEffect(
                    StoresEffect.OpenStoresDetailsScreen(
                        id, name, image, domain, projects
                    )
                )
            }
        }))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun getInstance() = StoresFragment()
    }
}