package myapplication.android.pixelpal.ui.games.games

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.app.App.Companion.appComponent
import myapplication.android.pixelpal.databinding.FragmentGamesBinding
import myapplication.android.pixelpal.ui.games.games.model.GamesShortDataUi
import myapplication.android.pixelpal.ui.games.games.mvi.GamesEffects
import myapplication.android.pixelpal.ui.games.games.mvi.GamesIntent
import myapplication.android.pixelpal.ui.games.games.mvi.GamesLocalDI
import myapplication.android.pixelpal.ui.games.games.mvi.GamesPartialState
import myapplication.android.pixelpal.ui.games.games.mvi.GamesState
import myapplication.android.pixelpal.ui.games.games.mvi.GamesStore
import myapplication.android.pixelpal.ui.games.games.mvi.GamesStoreFactory
import myapplication.android.pixelpal.ui.games.games.recycler_view.GamesShortModel
import myapplication.android.pixelpal.ui.games.games.recycler_view.LayoutType
import myapplication.android.pixelpal.ui.games.games.recycler_view.gridItem.GamesShortGridAdapter
import myapplication.android.pixelpal.ui.games.games.recycler_view.linear.GamesShortLinearAdapter
import myapplication.android.pixelpal.ui.games.games.recycler_view.one_item.GamesOneItemAdapter
import myapplication.android.pixelpal.ui.listener.GridPaginationScrollListener
import myapplication.android.pixelpal.ui.listener.LinearPaginationScrollListener
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviBaseFragment
import javax.inject.Inject

class GamesFragment @Inject constructor() : MviBaseFragment<
        GamesPartialState,
        GamesIntent,
        GamesState,
        GamesEffects>(R.layout.fragment_games) {
    private val gamesComponent by lazy {
        appComponent.gamesComponent().create()
    }
    private var id: Long = 0
    private var loading = false
    private var lastPage = false
    private val shortModels = mutableListOf<GamesShortModel>()
    private lateinit var adapter : ListAdapter<GamesShortModel, RecyclerView.ViewHolder>
    private var layoutType: LayoutType = LayoutType.Grid
    private var _binding: FragmentGamesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var gamesLocalDI: GamesLocalDI

    override val store: GamesStore by viewModels {
        GamesStoreFactory(
            gamesLocalDI.reducer,
            gamesLocalDI.actor
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gamesComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            store.sendIntent(GamesIntent.Init)
        }
        //Log.i("Id games getting data", id.toString())
        store.sendIntent(GamesIntent.GetGames(id))
    }

    fun setLayoutType(layoutType: LayoutType) {
        this.layoutType = layoutType
    }

    fun updateLayoutRecycler() {
        setLayoutManager()
    }

    override fun resolveEffect(effect: GamesEffects) {
        TODO("handle effects")
    }

    override fun render(state: GamesState) {
        when (state.ui) {
            is LceState.Content -> {
                binding.loading.root.visibility = GONE
                if (!lastPage) {
                    initRecycler(state.ui.data.items)
                    setLayoutManager()
                } else {
                    updateRecycler(state.ui.data.items)
                }
            }

            is LceState.Error -> {
                binding.loading.root.visibility = GONE
                Log.e("Error games pager", state.ui.throwable.message.toString())
            }

            LceState.Loading -> binding.loading.root.visibility = VISIBLE
        }
    }

    private fun updateRecycler(items: List<GamesShortDataUi>) {
        val newItems = mutableListOf<GamesShortModel>()
        for (i in items) {
            with(i) {
                newItems.add(
                    GamesShortModel(id, name, rating, releaseDate, playtime, image)
                )
            }
        }
        val startPosition = shortModels.size
        shortModels.addAll(newItems)
        binding.recyclerView.post {
            adapter.notifyItemRangeInserted(startPosition, newItems.size)
        }
        lastPage = false
        loading = false
    }

    private fun setLayoutManager() {
        with(binding) {
            adapter = when (layoutType) {
                LayoutType.Grid -> {
                    recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
                    GamesShortGridAdapter()
                }

                LayoutType.Linear -> {
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    GamesShortLinearAdapter()
                }

                LayoutType.OneItem -> {
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    GamesOneItemAdapter()
                }
            }
            recyclerView.adapter = adapter
            adapter.submitList(shortModels)
            addScrollRecyclerListener()
        }
    }

    private fun addScrollRecyclerListener() {
        val chosenId = id
        with(binding.recyclerView) {
            if (layoutManager is LinearLayoutManager) {
                addOnScrollListener(object :
                    LinearPaginationScrollListener(layoutManager as LinearLayoutManager) {
                    override fun isLastPage(): Boolean = lastPage

                    override fun isLoading(): Boolean = loading

                    override fun loadMoreItems() {
                        lastPage = true
                        loading = true
                        store.sendIntent(GamesIntent.GetGames(chosenId))
                    }

                })
            } else if (layoutManager is GridLayoutManager) {
                addOnScrollListener(object :
                    GridPaginationScrollListener(layoutManager as GridLayoutManager) {
                    override fun isLastPage(): Boolean = lastPage

                    override fun isLoading(): Boolean = loading

                    override fun loadMoreItems() {
                        lastPage = true
                        loading = true
                        store.sendIntent(GamesIntent.GetGames(chosenId))
                    }
                })
            }
        }
    }

    private fun initRecycler(games: List<GamesShortDataUi>): List<GamesShortModel> {
        for (i in games) {
            with(i) {
                shortModels.add(
                    GamesShortModel(id, name, rating, releaseDate, playtime, image)
                )
            }
        }
        return shortModels
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun getInstance(id: Long, layoutType: LayoutType) =
            GamesFragment().apply {
                this.id = id
                this.layoutType = layoutType
            }
    }
}