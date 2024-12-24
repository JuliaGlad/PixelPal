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
import myapplication.android.pixelpal.databinding.FragmentHomeBinding
import myapplication.android.pixelpal.ui.delegates.delegates.info_box.InfoBoxDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.info_box.InfoBoxDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.info_box.InfoBoxModel
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
import myapplication.android.pixelpal.ui.listener.ClickListener
import myapplication.android.pixelpal.ui.listener.RecyclerEndListener
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
    private val adapter : MainAdapter by lazy(LazyThreadSafetyMode.NONE) { initAdapter() }
    private var recyclerItems: MutableList<DelegateItem> = mutableListOf()
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

            is HomeLceState.UpdateTopContent -> updateRecycler(state.ui.data, TOP_ID)

            is HomeLceState.UpdateNextReleasesContent -> updateRecycler(state.ui.data, NEXT_ID)

            is HomeLceState.UpdateReleasesContent -> updateRecycler(state.ui.data, NEW_RELEASES_ID)
        }
    }

    private fun updateRecycler(items: GamesNewsListUi, requiredId: Int) {
        recyclerItems.forEach {
            if (it is NewsDelegateItem) {
                with((it.content() as NewsItemModel)) {
                    if (id == requiredId) {
                        isUpdated = true
                        val models = addReleaseItems(items)
                        adapter.notifyItemChanged(recyclerItems.indexOf(it), models)
                    }
                }
            }
        }
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

        val released = addReleaseItems(gamesNews.gamesReleased)
        val releaseThisMonth = addReleaseItems(gamesNews.gameMonthReleases)
        val topGames = addReleaseItems(gamesNews.gamesTop)
        recyclerItems = getMainItems(
            released,
            releaseThisMonth,
            topGames,
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
    ) = mutableListOf(
        NewsDelegateItem(
            NewsItemModel(
                NEW_RELEASES_ID,
                getString(R.string.new_releases),
                getString(R.string.there_is_no_new_games_released_this_month),
                released,
                object : ClickListener {
                    override fun onClick() {
                        TODO("open all new releases")
                    }
                },
                object : RecyclerEndListener {
                    override fun onEndReached() {
                        if (checkRecyclerForUpdate()) {
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
                object : ClickListener {
                    override fun onClick() {
                        TODO("Open all games releases in this month")
                    }
                },
                object : RecyclerEndListener {
                    override fun onEndReached() {
                        if (checkRecyclerForUpdate()) {
                            store.sendIntent(HomeIntent.GetNextReleases(currentDate, monthEndDate))
                        }
                    }
                }
            )
        ),
        InfoBoxDelegateItem(
            InfoBoxModel(
                4,
                getString(R.string.what_other_games_are_coming_out),
                object : ClickListener {
                    override fun onClick() {
                        TODO("Open dates dialog")
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
                object : ClickListener {
                    override fun onClick() {
                        TODO("open all top games")
                    }
                },
                object : RecyclerEndListener {
                    override fun onEndReached() {
                        if (checkRecyclerForUpdate()) {
                            store.sendIntent(HomeIntent.GetTop)
                        }
                    }
                }
            )
        )
    )

    private fun addReleaseItems(list: GamesNewsListUi): MutableList<ReleasesModel> {
        val items = mutableListOf<ReleasesModel>()
        for (i in list.games) {
            items.add(
                ReleasesModel(
                    list.games.indexOf(i),
                    i.name,
                    i.releaseDate.toString(),
                    i.genre,
                    i.uri.toString(),
                    object : ClickListener {
                        override fun onClick() {
                            TODO("Open game details screen")
                        }
                    }
                )
            )
        }
        return items
    }

    private fun checkRecyclerForUpdate(): Boolean =
        !binding.recycler.isComputingLayout

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