package myapplication.android.pixelpal.ui.platforms.fragments.store

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
import myapplication.android.pixelpal.databinding.FragmnetStoreBinding
import myapplication.android.pixelpal.di.DaggerAppComponent
import myapplication.android.pixelpal.ui.listener.GridPaginationScrollListener
import myapplication.android.pixelpal.ui.main.MainActivity
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviBaseFragment
import myapplication.android.pixelpal.ui.platforms.PlatformPager
import myapplication.android.pixelpal.ui.platforms.fragments.store.di.DaggerStoresComponent
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

@SuppressLint("NotifyDataSetChanged")
class StoresFragment :
    MviBaseFragment<StoresPartialState, StoresIntent, StoresState, StoresEffect>(R.layout.fragmnet_store), PlatformPager {
    private var query: String = ""
    private var previousQuery: String = ""
    private val adapter = StoreAdapter()
    private val models = mutableListOf<StoreModel>()
    private var _binding: FragmnetStoreBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var storesLocalDI: StoresLocalDI

    var loading = false
    var needUpdate = false

    override val store: StoresStore by viewModels {
        StoresStoreFactory(
            storesLocalDI.reducer, storesLocalDI.actor
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerStoresComponent.factory().create(appComponent).inject(this)
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
        if (query.isEmpty()) store.sendIntent(StoresIntent.GetStores)
        else store.sendIntent(StoresIntent.GetStoresByQuery(query))
    }

    override fun updateQuery(query: String) {
        previousQuery = this.query
        this.query = query
        if (isAdded) {
            if (previousQuery != query) {
                store.sendIntent(StoresIntent.Init)
                models.clear()
                adapter.notifyDataSetChanged()
                previousQuery = query
            }
            needUpdate = true
            if (query.isEmpty()) store.sendIntent(StoresIntent.GetStores)
            else store.sendIntent(StoresIntent.GetStoresByQuery(query))
        }
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
                if (!needUpdate) {
                    initRecycler(state.ui.data)
                } else if (query.isNotEmpty()) {
                    updateQueryRecycler(state.ui.data.stores)
                } else {
                    updateRecycler(state.ui.data.stores)
                    if (models.size <= 4) store.sendIntent(StoresIntent.GetStores)
                }
            }

            is LceState.Error -> {
                binding.loadingRecycler.root.visibility = GONE
                Log.e("Stores error", state.ui.throwable.message.toString())
            }

            LceState.Loading -> binding.loadingRecycler.root.visibility = VISIBLE
        }
    }


    private fun updateQueryRecycler(stores: List<StoreUi>){
        if (previousQuery != query){
            models.clear()
            adapter.notifyDataSetChanged()
            previousQuery = query
        }
        val startPosition = models.size
        val newItems = getStoreItems(stores)
        models.addAll(newItems)
        adapter.notifyItemRangeInserted(startPosition, models.size)
        needUpdate = false
        loading = false
        binding.loadingRecycler.root.visibility = GONE
    }

    private fun updateRecycler(stores: List<StoreUi>) {
        val newItems = getStoreItems(stores)
        val positionStart = models.size
        models.addAll(newItems)
        adapter.notifyItemRangeInserted(positionStart, newItems.size)
        loading = false
        needUpdate = false
    }

    private fun getStoreItems(stores: List<StoreUi>): MutableList<StoreModel> {
        val newItems = mutableListOf<StoreModel>()
        for (i in stores) {
            with(i) {
                newItems.add(
                    stores.indexOf(i), id, name, domain, projects, image
                )
            }
        }
        return newItems
    }

    private fun initRecycler(stores: StoresUiList) {
        binding.recyclerView.adapter = adapter
        val items = getStoreItems(stores.stores)
        models.addAll(items)
        adapter.submitList(models)
        addScrollRecyclerListener()
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
                        needUpdate = true
                        loading = true
                        if (query.isEmpty()) store.sendIntent(StoresIntent.GetStores)
                        else store.sendIntent(StoresIntent.GetStoresByQuery(query))
                    }
                }

            })
        }
    }

    private fun MutableList<StoreModel>.add(
        id: Int, storeId: Int, name: String, domain: String?, projects: Int?, image: String
    ) {
        add(StoreModel(id, storeId, name, domain, projects, image) {
            store.sendEffect(
                StoresEffect.OpenStoresDetailsScreen(
                    storeId, name, image, domain, projects
                )
            )
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun getInstance() = StoresFragment()
    }
}