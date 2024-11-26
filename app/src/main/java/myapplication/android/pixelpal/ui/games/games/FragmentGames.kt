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
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.databinding.FragmentGamesBinding
import myapplication.android.pixelpal.di.DiContainer
import myapplication.android.pixelpal.ui.games.games.model.GamesShortDataUi
import myapplication.android.pixelpal.ui.games.games.mvi.GamesEffects
import myapplication.android.pixelpal.ui.games.games.mvi.GamesIntent
import myapplication.android.pixelpal.ui.games.games.mvi.GamesPartialState
import myapplication.android.pixelpal.ui.games.games.mvi.GamesState
import myapplication.android.pixelpal.ui.games.games.mvi.GamesStore
import myapplication.android.pixelpal.ui.games.games.recycler_view.GamesShortModel
import myapplication.android.pixelpal.ui.games.games.recycler_view.LayoutType
import myapplication.android.pixelpal.ui.games.games.recycler_view.gridItem.GamesShortGridAdapter
import myapplication.android.pixelpal.ui.games.games.recycler_view.linear.GamesShortLinearAdapter
import myapplication.android.pixelpal.ui.games.games.recycler_view.one_item.GamesOneItemAdapter
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviBaseFragment

class FragmentGames : MviBaseFragment<
        GamesPartialState,
        GamesIntent,
        GamesState,
        GamesEffects>(R.layout.fragment_games) {
    private var id: Long = 0
    private val games = mutableListOf<GamesShortDataUi>()
    private var layoutType: LayoutType = LayoutType.Grid
    private var _binding: FragmentGamesBinding? = null
    private val binding get() = _binding!!
    override val store: GamesStore by viewModels { DiContainer.gamesStoreFactory }

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
        if (savedInstanceState == null){
            store.sendIntent(GamesIntent.Init)
        }
        store.sendIntent(GamesIntent.GetGames(id))
    }

    fun setLayoutType(layoutType: LayoutType){
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
                games.addAll(state.ui.data.items)
                setLayoutManager()
            }

            is LceState.Error -> {
                binding.loading.root.visibility = GONE
                Log.e("Error games pager", state.ui.throwable.message.toString())
            }

            LceState.Loading -> binding.loading.root.visibility = VISIBLE
        }
    }

    private fun setLayoutManager() {
        with(binding) {
            val adapter = when (layoutType) {
                LayoutType.Grid -> {
                    Log.i("Set to grid", "to grid")
                    recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
                    GamesShortGridAdapter()
                }

                LayoutType.Linear -> {
                    Log.i("Set to linear", "to linear")
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    GamesShortLinearAdapter()
                }

                LayoutType.OneItem -> {
                    Log.i("Set to one item", "to one item")
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    GamesOneItemAdapter()
                }
            }
            recyclerView.adapter = adapter
            adapter.submitList(initRecycler())
        }
    }

    private fun initRecycler(): List<GamesShortModel>{
        val shortModels = mutableListOf<GamesShortModel>()
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
            FragmentGames().apply {
                this.id = id
                this.layoutType = layoutType
            }
    }
}