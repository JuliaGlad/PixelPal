package myapplication.android.pixelpal.ui.games.games

import android.annotation.SuppressLint
import android.content.Context
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
import myapplication.android.pixelpal.ui.main.MainActivity
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviBaseFragment
import javax.inject.Inject

@SuppressLint("NotifyDataSetChanged")
class GamesFragment @Inject constructor() : MviBaseFragment<
        GamesPartialState,
        GamesIntent,
        GamesState,
        GamesEffects>(R.layout.fragment_games) {
    private val gamesComponent by lazy {
        appComponent.gamesComponent().create()
    }
    private var id: Long = 0
    private var genre: String = ""
    private var query: String = ""
    private var previousQuery = ""
    private var loading = false
    private var needUpdate = false
    private val shortModels = mutableListOf<GamesShortModel>()
    private lateinit var adapter: ListAdapter<GamesShortModel, RecyclerView.ViewHolder>
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
        if (query.isEmpty()) store.sendIntent(GamesIntent.GetGames(id))
        else store.sendIntent(GamesIntent.GetGamesByQuery(id, query))
    }

    fun setLayoutType(layoutType: LayoutType) {
        this.layoutType = layoutType
    }

    fun updateLayoutRecycler() {
        setLayoutManager()
    }

    fun updateQuery(query: String) {
        previousQuery = this.query
        this.query = query
        if (isAdded) {
            if (previousQuery != query) store.sendIntent(GamesIntent.Init)
            needUpdate = true
            if (query.isNotEmpty()) store.sendIntent(GamesIntent.GetGamesByQuery(id, query))
            else {
                shortModels.clear()
                adapter.notifyDataSetChanged()
                store.sendIntent(GamesIntent.GetGames(id))
            }
        }
    }

    override fun resolveEffect(effect: GamesEffects) {
        when (effect) {
            GamesEffects.OpenFilters -> TODO()
            is GamesEffects.OpenGameDetails -> {
                with(effect) {
                    (activity as MainActivity).openGameDetailsActivity(
                        gameId, name, genre, releaseDate, image
                    )
                }
            }
        }
    }

    override fun render(state: GamesState) {
        when (state.ui) {
            is LceState.Content -> {
                binding.loading.root.visibility = GONE
                if (!needUpdate) {
                    shortModels.addAll(getGameShortModels(state.ui.data.items))
                    setLayoutManager()
                } else if (query.isNotEmpty()){
                    updateQueryRecycler(state.ui.data.items)
                    if (shortModels.size <= 4) store.sendIntent(GamesIntent.GetGamesByQuery(id, query))
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
    private fun updateQueryRecycler(items: List<GamesShortDataUi>) {
        if (previousQuery != query){
            shortModels.clear()
            adapter.notifyDataSetChanged()
            previousQuery = query
        }
        val startPosition = shortModels.size
        val models = getGameShortModels(items)
        shortModels.addAll(models)
        adapter.notifyItemRangeInserted(startPosition, models.size)
        needUpdate = false
        loading = false
    }

    private fun updateRecycler(items: List<GamesShortDataUi>) {
        val newItems = getGameShortModels(items)
        val startPosition = shortModels.size
        shortModels.addAll(newItems)
        adapter.notifyItemRangeInserted(startPosition, newItems.size)
        needUpdate = false
        loading = false
    }

    private fun setLayoutManager() {
        if (previousQuery != query) previousQuery = query
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
                    override fun isLastPage(): Boolean = needUpdate

                    override fun isLoading(): Boolean = loading

                    override fun loadMoreItems() {
                        needUpdate = true
                        loading = true
                       if (query.isEmpty())
                        store.sendIntent(GamesIntent.GetGames(chosenId))
                        else store.sendIntent(GamesIntent.GetGamesByQuery(chosenId, query))
                    }

                })
            } else if (layoutManager is GridLayoutManager) {
                addOnScrollListener(object :
                    GridPaginationScrollListener(layoutManager as GridLayoutManager) {
                    override fun isLastPage(): Boolean = needUpdate

                    override fun isLoading(): Boolean = loading

                    override fun loadMoreItems() {
                        needUpdate = true
                        loading = true
                        if (query.isEmpty()) store.sendIntent(GamesIntent.GetGames(chosenId))
                        else store.sendIntent(GamesIntent.GetGamesByQuery(chosenId, query))
                    }
                })
            }
        }
    }

    private fun getGameShortModels(games: List<GamesShortDataUi>): List<GamesShortModel> {
        val newModels = mutableListOf<GamesShortModel>()
        for (i in games) {
            with(i) {
                newModels.add(
                    GamesShortModel(gameId, name, rating, releaseDate, playtime, image,
                        {
                            store.sendEffect(
                                GamesEffects.OpenGameDetails(
                                    gameId,
                                    name,
                                    releaseDate!!,
                                    image!!
                                )
                            )
                        })
                )
            }
        }
        return newModels
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun getInstance(id: Long, layoutType: LayoutType, genre: String, query: String) =
            GamesFragment().apply {
                this.id = id
                this.layoutType = layoutType
                this.genre = genre
                this.query = query
            }
    }
}