package myapplication.android.pixelpal.ui.profile.favorite_games

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.TEXT_ALIGNMENT_CENTER
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.app.Constants
import myapplication.android.pixelpal.databinding.FragmentFavoriteGamesBinding
import myapplication.android.pixelpal.di.DaggerAppComponent
import myapplication.android.pixelpal.ui.games.games.recycler_view.GamesShortModel
import myapplication.android.pixelpal.ui.games.games.recycler_view.linear.GamesShortLinearAdapter
import myapplication.android.pixelpal.ui.home.model.GamesMainInfoListUi
import myapplication.android.pixelpal.ui.home.model.GamesUi
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviBaseFragment
import myapplication.android.pixelpal.ui.mvi.MviStore
import myapplication.android.pixelpal.ui.profile.favorite_games.di.DaggerFavoriteGamesComponent
import myapplication.android.pixelpal.ui.profile.favorite_games.mvi.FavoriteGamesEffect
import myapplication.android.pixelpal.ui.profile.favorite_games.mvi.FavoriteGamesIntent
import myapplication.android.pixelpal.ui.profile.favorite_games.mvi.FavoriteGamesLocalDI
import myapplication.android.pixelpal.ui.profile.favorite_games.mvi.FavoriteGamesPartialState
import myapplication.android.pixelpal.ui.profile.favorite_games.mvi.FavoriteGamesState
import myapplication.android.pixelpal.ui.profile.favorite_games.mvi.FavoriteGamesStoreFactory
import javax.inject.Inject

class FavoriteGamesFragment : MviBaseFragment<
        FavoriteGamesPartialState,
        FavoriteGamesIntent,
        FavoriteGamesState,
        FavoriteGamesEffect>(R.layout.fragment_favorite_games) {

    private var _binding: FragmentFavoriteGamesBinding? = null
    private val binding get() = _binding!!
    private val models: MutableList<GamesShortModel> = mutableListOf()
    private val adapter by lazy { GamesShortLinearAdapter() }
    private var launcher: ActivityResultLauncher<Intent>? = null

    @Inject
    lateinit var localDI: FavoriteGamesLocalDI

    override val store: MviStore<FavoriteGamesPartialState, FavoriteGamesIntent, FavoriteGamesState, FavoriteGamesEffect>
            by viewModels {
                FavoriteGamesStoreFactory(
                    localDI.actor,
                    localDI.reducer
                )
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerFavoriteGamesComponent.factory().create(appComponent).inject(this)
        launcher = setActivityResultLauncher()
    }

    private fun setActivityResultLauncher() = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            if (data != null) {
                val id = data.getLongExtra(Constants.GAME_ID_ARG, 0L)
                var position = -1
                for (model in models) {
                    if (model.id == id) position = models.indexOf(model)
                }
                models.removeAt(position)
                adapter.notifyItemRemoved(position)
                if (models.isEmpty()) initNoItems()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteGamesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            store.sendIntent(FavoriteGamesIntent.Init)
        }
        store.sendIntent(FavoriteGamesIntent.GetGames)
    }

    override fun resolveEffect(effect: FavoriteGamesEffect) {
        when (effect) {
            FavoriteGamesEffect.NavigateBack -> activity?.finish()
            is FavoriteGamesEffect.OpenGameDetails -> {
                with(effect) {
                    launcher?.let {
                        (activity as FavoriteGamesActivity).openGameDetailsActivity(
                            gameId = gameId,
                            name = name,
                            genres = genres,
                            released = released!!,
                            image = image!!,
                            it
                        )
                    }
                }
            }
        }
    }

    override fun render(state: FavoriteGamesState) {
        when (state.ui) {
            is LceState.Content -> {
                binding.loading.root.visibility = GONE
                initRecycler(state.ui.data)
                initBackButton()
            }
            is LceState.Error -> {
                binding.loading.root.visibility = GONE
                Log.i("FavoriteGamesError", state.ui.throwable.message.toString())
            }
            LceState.Loading -> binding.loading.root.visibility = VISIBLE
        }
    }

    private fun initBackButton() {
        binding.iconBack.setOnClickListener {
            store.sendEffect(FavoriteGamesEffect.NavigateBack)
        }
    }

    private fun initRecycler(games: GamesMainInfoListUi) {
        with(binding) {
            if (games.games.isNotEmpty()) {
                models.addAll(getGameShortModels(games))
                recyclerView.adapter = adapter
                adapter.submitList(models)
            } else {
                initNoItems()
            }
        }
    }

    private fun initNoItems() {
        with(binding) {
            noItems.root.visibility = VISIBLE
            noItems.title.setShimmerText(getString(R.string.looks_like_you_dont_have_any_favorite_games_yet))
            noItems.title.setShimmerTextAlignment(TEXT_ALIGNMENT_CENTER)
        }
    }

    private fun getGameShortModels(data: GamesMainInfoListUi): List<GamesShortModel> {
        val models = mutableListOf<GamesShortModel>()
        for (i in data.games) {
            with(i) {
                addGamesToList(models)
            }
        }
        return models
    }

    private fun GamesUi.addGamesToList(models: MutableList<GamesShortModel>) =
        models.add(
            GamesShortModel(
                gameId, name, rating?.toInt(), releaseDate, playTime, uri.toString(),
                {
                    store.sendEffect(
                        FavoriteGamesEffect.OpenGameDetails(
                            gameId,
                            genre,
                            name,
                            releaseDate!!,
                            uri.toString()
                        )
                    )
                }
            )
        )

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}