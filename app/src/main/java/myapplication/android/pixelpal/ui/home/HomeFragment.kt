package myapplication.android.pixelpal.ui.home

import android.icu.util.Calendar
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.app.App.Companion.appComponent
import myapplication.android.pixelpal.app.Constants
import myapplication.android.pixelpal.databinding.FragmentHomeBinding
import myapplication.android.pixelpal.ui.delegates.delegates.info_box.InfoBoxDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.news_main.NewsDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.news_main.NewsDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.news_main.NewsItemModel
import myapplication.android.pixelpal.ui.delegates.main.DelegateItem
import myapplication.android.pixelpal.ui.delegates.main.MainAdapter
import myapplication.android.pixelpal.ui.home.model.GamesNewsListUi
import myapplication.android.pixelpal.ui.home.mvi.HomeContentResult
import myapplication.android.pixelpal.ui.home.mvi.HomeEffect
import myapplication.android.pixelpal.ui.home.mvi.HomeIntent
import myapplication.android.pixelpal.ui.home.mvi.HomeLceState
import myapplication.android.pixelpal.ui.home.mvi.HomeLocalDI
import myapplication.android.pixelpal.ui.home.mvi.HomePartialState
import myapplication.android.pixelpal.ui.home.mvi.HomeState
import myapplication.android.pixelpal.ui.home.mvi.HomeStore
import myapplication.android.pixelpal.ui.home.mvi.HomeStoreFactory
import myapplication.android.pixelpal.ui.home.recycler_view.releases.ReleasesModel
import myapplication.android.pixelpal.ui.listener.RecyclerEndListener
import myapplication.android.pixelpal.ui.main.MainActivity
import myapplication.android.pixelpal.ui.mvi.MviBaseFragment
import java.util.Date
import javax.inject.Inject

class HomeFragment :
    MviBaseFragment<
            HomePartialState,
            HomeIntent,
            HomeState,
            HomeEffect
            >(R.layout.fragment_home) {
    private val homeComponent by lazy {
        appComponent.homeComponent().create()
    }
    private val adapter: MainAdapter by lazy(LazyThreadSafetyMode.NONE) { initAdapter() }
    private var recyclerItems: MutableList<DelegateItem> = mutableListOf()
    private var newReleasesSize = 0
    private var topGamesSize = 0
    private var nextReleasesSize = 0
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var homeLocalDI: HomeLocalDI

    override val store: HomeStore by viewModels {
        HomeStoreFactory(
            homeLocalDI.reducer,
            homeLocalDI.actor
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            store.sendIntent(HomeIntent.Init)
        }
        getGames()
    }

    override fun resolveEffect(effect: HomeEffect) {
        when (effect) {
            is HomeEffect.OpenGameDetailsScreen -> {
                with(effect) {
                    (activity as MainActivity).openGameDetailsActivity(
                        gameId,
                        name,
                        genres,
                        released.toString(),
                        image.toString()
                    )
                }
            }

            is HomeEffect.OpenAllTopGamesScreen -> {
                (activity as MainActivity).openAllTopGamesActivity(Constants.TOP_ID)
            }

            is HomeEffect.OpenAllCurrentReleasesScreen -> {
                val (currentDate, _, monthStartDate) = getVariables()
                (activity as MainActivity).openAllTopGamesActivity(
                    Constants.RELEASES_ID,
                    currentDate = currentDate,
                    startDate = monthStartDate
                )
            }

            is HomeEffect.OpenAllNextReleasesScreen -> {
                val (currentDate, monthEndDate, _) = getVariables()
                (activity as MainActivity).openAllTopGamesActivity(
                    Constants.RELEASES_NEXT_ID,
                    currentDate = currentDate,
                    endDate = monthEndDate
                )
            }
        }
    }

    override fun render(state: HomeState) {
        when (state.ui) {
            is HomeLceState.Content -> {
                binding.progressBar.root.visibility = GONE
                initRecycler(state.ui.data)
            }

            is HomeLceState.Error -> {
                binding.progressBar.root.visibility = GONE
                Log.e(TAG, "${state.ui.throwable.message}")
            }

            HomeLceState.Loading -> binding.progressBar.root.visibility = VISIBLE

            is HomeLceState.UpdateTopContent -> topGamesSize += updateRecycler(
                state.ui.data,
                TOP_ID,
                topGamesSize
            )

            is HomeLceState.UpdateNextReleasesContent -> nextReleasesSize += updateRecycler(
                state.ui.data,
                NEXT_ID,
                newReleasesSize
            )

            is HomeLceState.UpdateReleasesContent -> newReleasesSize = updateRecycler(
                state.ui.data,
                NEW_RELEASES_ID,
                newReleasesSize
            )
        }
    }

    private fun updateRecycler(items: GamesNewsListUi, requiredId: Int, itemsSize: Int): Int {
        var newSize = 0
        recyclerItems.forEach {
            if (it is NewsDelegateItem) {
                with((it.content() as NewsItemModel)) {
                    if (id == requiredId) {
                        isUpdated = true
                        val result = addReleaseItems(items, itemsSize)
                        val models = result.first
                        newSize = result.second
                        adapter.notifyItemChanged(recyclerItems.indexOf(it), models)
                    }
                }
            }
        }
        return newSize
    }

    private fun getGames() {
        val (currentDate, monthEndDate, monthStartDate) = getVariables()
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                store.sendIntent(
                    HomeIntent.GetGames(
                        currentDate,
                        monthEndDate,
                        monthStartDate
                    )
                )
            }
        }
    }

    private fun getVariables(): Triple<String, String, String> {
        val cal = Calendar.getInstance()

        val currentDate = DateFormat.format("yyyy-MM-dd", Date()).toString()
        val monthEndDate =
            DateFormat.format("yyyy-MM-${cal.getActualMaximum(Calendar.DATE)}", Date()).toString()
        val monthStartDate = DateFormat.format("yyyy-MM-01", Date()).toString()

        return Triple(currentDate, monthEndDate, monthStartDate)
    }

    private fun initRecycler(gamesNews: HomeContentResult) {
        val (currentDate, monthEndDate, monthStartDate) = getVariables()

        val released = addReleaseItems(gamesNews.gamesReleased, newReleasesSize)
        val releaseThisMonth = addReleaseItems(gamesNews.gameMonthReleases, nextReleasesSize)
        val topGames = addReleaseItems(gamesNews.gamesTop, topGamesSize)
        newReleasesSize += released.second
        nextReleasesSize += releaseThisMonth.second
        topGamesSize += topGames.second
        recyclerItems = getMainItems(
            released.first,
            releaseThisMonth.first,
            topGames.first,
            currentDate,
            monthEndDate,
            monthStartDate
        )
        adapter.submitList(
            recyclerItems
        )
    }

    private fun initAdapter(): MainAdapter {
        val adapter = MainAdapter()
        adapter.addDelegate(NewsDelegate())
        adapter.addDelegate(InfoBoxDelegate())
        binding.recycler.adapter = adapter

        return adapter
    }

    private fun getMainItems(
        released: MutableList<ReleasesModel>,
        releaseThisMonth: MutableList<ReleasesModel>,
        topGames: MutableList<ReleasesModel>,
        currentDate: String,
        monthEndDate: String,
        monthStartDate: String
    ) = mutableListOf<DelegateItem>(
        NewsDelegateItem(
            NewsItemModel(
                NEW_RELEASES_ID,
                getString(R.string.new_releases),
                getString(R.string.there_is_no_new_games_released_this_month),
                released,
                { store.sendEffect(HomeEffect.OpenAllCurrentReleasesScreen) },
                object : RecyclerEndListener {
                    override fun onEndReached() {
                        if (checkRecyclerForUpdate(newReleasesSize)) {
                            store.sendIntent(HomeIntent.GetReleases(monthStartDate, currentDate))
                        }
                    }
                }
            )
        ),
        NewsDelegateItem(
            NewsItemModel(
                NEXT_ID,
                getString(R.string.release_this_month),
                getString(R.string.there_is_no_games_releases_this_month),
                releaseThisMonth,
                { store.sendEffect(HomeEffect.OpenAllNextReleasesScreen) },
                object : RecyclerEndListener {
                    override fun onEndReached() {
                        if (checkRecyclerForUpdate(nextReleasesSize)) {
                            store.sendIntent(HomeIntent.GetNextReleases(currentDate, monthEndDate))
                        }
                    }
                }
            )
        ),
        NewsDelegateItem(
            NewsItemModel(
                TOP_ID,
                getString(R.string.all_time_top),
                "",
                topGames,
                { store.sendEffect(HomeEffect.OpenAllTopGamesScreen) },
                object : RecyclerEndListener {
                    override fun onEndReached() {
                        if (checkRecyclerForUpdate(topGamesSize)) {
                            store.sendIntent(HomeIntent.GetTop)
                        }
                    }
                }
            )
        )
    )

    private fun addReleaseItems(list: GamesNewsListUi, itemsSize: Int): Pair<MutableList<ReleasesModel>, Int> {
        var checkingSize = itemsSize
        val items = mutableListOf<ReleasesModel>()
        for (i in list.games) {
            if (checkingSize >= 30) break
            items.add(
                with(i) {
                    ReleasesModel(
                        list.games.indexOf(i),
                        name,
                        releaseDate.toString(),
                        genre,
                        uri.toString()
                    ) {
                        store.sendEffect(
                            HomeEffect.OpenGameDetailsScreen(
                                gameId,
                                name,
                                genre,
                                releaseDate,
                                uri
                            )
                        )
                    }
                }
            )
            checkingSize++
        }
        return Pair(items, checkingSize)
    }

    private fun checkRecyclerForUpdate(items: Int): Boolean =
        !binding.recycler.isComputingLayout && items < 30

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "HomeFragmentException"
        private const val NEW_RELEASES_ID = 1
        private const val TOP_ID = 2
        private const val NEXT_ID = 3
    }

}