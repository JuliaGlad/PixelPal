package myapplication.android.pixelpal.ui.all_games

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
import myapplication.android.pixelpal.app.Constants.Companion.ADDITIONS_AND_PARENT_ID
import myapplication.android.pixelpal.app.Constants.Companion.ALL_INTENT_ID
import myapplication.android.pixelpal.app.Constants.Companion.CURRENT_DATE
import myapplication.android.pixelpal.app.Constants.Companion.END_DATE
import myapplication.android.pixelpal.app.Constants.Companion.GAME_GENRES_ARG
import myapplication.android.pixelpal.app.Constants.Companion.GAME_ID_ARG
import myapplication.android.pixelpal.app.Constants.Companion.RELEASES_ID
import myapplication.android.pixelpal.app.Constants.Companion.RELEASES_NEXT_ID
import myapplication.android.pixelpal.app.Constants.Companion.SAME_SERIES_ID
import myapplication.android.pixelpal.app.Constants.Companion.START_DATE
import myapplication.android.pixelpal.app.Constants.Companion.TOP_ID
import myapplication.android.pixelpal.databinding.FragmentAllGamesBinding
import myapplication.android.pixelpal.ui.all_games.fragment_argument.AllArgument
import myapplication.android.pixelpal.ui.all_games.mvi.AllGamesEffect
import myapplication.android.pixelpal.ui.all_games.mvi.AllGamesIntent
import myapplication.android.pixelpal.ui.all_games.mvi.AllGamesLocalDI
import myapplication.android.pixelpal.ui.all_games.mvi.AllGamesPartialState
import myapplication.android.pixelpal.ui.all_games.mvi.AllGamesState
import myapplication.android.pixelpal.ui.all_games.mvi.AllGamesStoreFactory
import myapplication.android.pixelpal.ui.games.games.model.GamesShortDataUi
import myapplication.android.pixelpal.ui.games.games.model.GamesShortDataUiList
import myapplication.android.pixelpal.ui.games.games.recycler_view.GamesShortModel
import myapplication.android.pixelpal.ui.games.games.recycler_view.linear.GamesShortLinearAdapter
import myapplication.android.pixelpal.ui.home.model.GamesNewsListUi
import myapplication.android.pixelpal.ui.home.model.GamesUi
import myapplication.android.pixelpal.ui.listener.ClickListener
import myapplication.android.pixelpal.ui.listener.LinearPaginationScrollListener
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviBaseFragment
import myapplication.android.pixelpal.ui.mvi.MviStore
import javax.inject.Inject

class AllGamesFragment : MviBaseFragment<
        AllGamesPartialState,
        AllGamesIntent,
        AllGamesState,
        AllGamesEffect>(R.layout.fragment_all_games) {

    private val adapter by lazy { GamesShortLinearAdapter() }

    private val component by lazy {
        appComponent.allGamesComponent().create()
    }

    private val gamesShortModels = mutableListOf<GamesShortModel>()

    @Inject
    lateinit var localDi: AllGamesLocalDI

    override val store: MviStore<AllGamesPartialState, AllGamesIntent, AllGamesState, AllGamesEffect>
            by viewModels {
                AllGamesStoreFactory(
                    localDi.reducer,
                    localDi.actor
                )
            }

    private var loading = false
    private var lastPage = false

    private val dataId: Int by lazy { getArgumentId() }
    private var arguments: AllArgument? = null
    private var intent: AllGamesIntent? = null

    private var _binding: FragmentAllGamesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        getOtherArguments(dataId)
    }

    private fun getArgumentId() = activity?.intent?.getIntExtra(ALL_INTENT_ID, 0)!!

    private fun getOtherArguments(dataId: Int) {
        when (dataId) {
            RELEASES_ID -> {
                arguments = AllArgument.CurrentReleasesAllArgument(
                    activity?.intent?.getStringExtra(START_DATE)!!,
                    activity?.intent?.getStringExtra(CURRENT_DATE)!!
                )
                with(arguments as AllArgument.CurrentReleasesAllArgument) {
                    intent = AllGamesIntent.GetAllCurrentReleases(
                        startDate,
                        currentDate
                    )
                }
            }

            RELEASES_NEXT_ID -> {
                arguments = AllArgument.NextReleasesAllArgument(
                    activity?.intent?.getStringExtra(END_DATE)!!,
                    activity?.intent?.getStringExtra(CURRENT_DATE)!!
                )
                with(arguments as AllArgument.NextReleasesAllArgument) {
                    intent = AllGamesIntent.GetAllNextReleases(
                        endDate,
                        currentDate
                    )
                }
            }

            TOP_ID -> intent = AllGamesIntent.GetGames

            SAME_SERIES_ID -> {
                arguments = AllArgument.GameDetailsArgument(
                    activity?.intent?.getStringExtra(GAME_GENRES_ARG)!!,
                    activity?.intent?.getLongExtra(GAME_ID_ARG, 0L)!!
                )
                intent = AllGamesIntent.GetAllSameSeries(
                    (arguments as AllArgument.GameDetailsArgument).gameId
                )
            }

            ADDITIONS_AND_PARENT_ID -> {
                arguments = AllArgument.GameDetailsArgument(
                    activity?.intent?.getStringExtra(GAME_GENRES_ARG)!!,
                    activity?.intent?.getLongExtra(GAME_ID_ARG, 0L)!!
                )
                intent = AllGamesIntent.GetGameParentSeries(
                    (arguments as AllArgument.GameDetailsArgument).gameId
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllGamesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            store.sendIntent(AllGamesIntent.Init)
        }
        initButtonBack()
        store.sendIntent(intent!!)
    }

    private fun initButtonBack() {
        binding.buttonBack.setOnClickListener { store.sendEffect(AllGamesEffect.NavigateBack) }
    }

    override fun resolveEffect(effect: AllGamesEffect) {
        when (effect) {
            AllGamesEffect.NavigateBack -> activity?.finish()
            is AllGamesEffect.OpenGameDetails -> {
                with(effect) {
                    (activity as AllGamesActivity).openGameDetailsActivity(
                        gameId,
                        name,
                        genres,
                        releaseDate,
                        image
                    )
                }
            }
        }
    }

    override fun render(state: AllGamesState) {
        when (state.ui) {
            is LceState.Content -> {
                binding.loading.root.visibility = GONE
                when(state.ui.data){
                    is GamesNewsListUi ->{
                        initOrUpdate(
                            state.ui.data,
                            ::initGameNewsRecycler,
                            ::updateGamesNewsRecycler
                        )
                    }
                    is GamesShortDataUiList -> {
                        initOrUpdate(
                            state.ui.data,
                            ::initGameNewsRecycler,
                            ::updateGamesShortDataRecycler
                        )
                    }
                }
            }

            is LceState.Error -> {
                binding.loading.root.visibility = GONE
                Log.e("Error", state.ui.throwable.message.toString())
            }

            LceState.Loading -> binding.loading.root.visibility = VISIBLE
        }
    }

    private fun <T> initOrUpdate(
        data: T,
        init: (T) -> Unit,
        update: (T) -> Unit
    ) {
        if (!lastPage) {
            init.invoke(data)
        } else {
            update.invoke(data)
        }
    }

    private fun updateGamesShortDataRecycler(items: GamesShortDataUiList) {
        val models = addDataShortModelFromGamesShortData(items, (arguments as AllArgument.GameDetailsArgument).genre)
        val startPosition = gamesShortModels.size - 1
        gamesShortModels.addAll(models)
        adapter.notifyItemRangeInserted(startPosition, models.size)
    }

    private fun updateGamesNewsRecycler(items: GamesNewsListUi) {
        val models = addDataShortModelFromNewsUi(items)
        val startPosition = gamesShortModels.size - 1
        gamesShortModels.addAll(models)
        adapter.notifyItemRangeInserted(startPosition, models.size)
    }

    private fun addDataShortModelFromNewsUi(data: GamesNewsListUi): List<GamesShortModel> {
        val models = mutableListOf<GamesShortModel>()
        for (i in data.games) {
            with(i) {
                addGamesToList(models)
            }
        }
        return models
    }

    private fun addDataShortModelFromGamesShortData(data: GamesShortDataUiList, genre: String): List<GamesShortModel> {
        val models = mutableListOf<GamesShortModel>()
        for (i in data.items) {
            with(i) {
                addGamesToList(models, genre)
            }
        }
        return models
    }

    private fun initGameNewsRecycler(data: GamesShortDataUiList) {
        val genre = (arguments as AllArgument.GameDetailsArgument).genre
        gamesShortModels.addAll(addDataShortModelFromGamesShortData(data, genre))
        binding.recyclerView.adapter = adapter
        adapter.submitList(gamesShortModels)
        addScrollRecyclerListener()
    }

    private fun initGameNewsRecycler(data: GamesNewsListUi) {
        gamesShortModels.addAll(addDataShortModelFromNewsUi(data))
        binding.recyclerView.adapter = adapter
        adapter.submitList(gamesShortModels)
        addScrollRecyclerListener()
    }

    private fun addScrollRecyclerListener() {
        with(binding.recyclerView) {
            addOnScrollListener(object :
                LinearPaginationScrollListener(layoutManager as LinearLayoutManager) {
                override fun isLastPage(): Boolean = lastPage

                override fun isLoading(): Boolean = loading

                override fun loadMoreItems() {
                    lastPage = true
                    loading = true
                    store.sendIntent(intent!!)
                }

            })
        }
    }


    private fun GamesUi.addGamesToList(models: MutableList<GamesShortModel>) =
        models.add(
            GamesShortModel(
                gameId, name, rating?.toInt(), releaseDate, playTime, uri.toString(),
                object : ClickListener {
                    override fun onClick() {
                        store.sendEffect(
                            AllGamesEffect.OpenGameDetails(
                                gameId,
                                genre,
                                name,
                                releaseDate!!,
                                uri.toString()
                            )
                        )
                    }
                }
            )
        )

    private fun GamesShortDataUi.addGamesToList(
        models: MutableList<GamesShortModel>,
        genre: String
    ) =
        models.add(
            GamesShortModel(
                gameId, name, rating, releaseDate, playtime, image.toString(),
                object : ClickListener {
                    override fun onClick() {
                        store.sendEffect(
                            AllGamesEffect.OpenGameDetails(
                                gameId,
                                genre,
                                name,
                                releaseDate!!,
                                image.toString()
                            )
                        )
                    }
                }
            )
        )

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}