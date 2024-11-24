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
import myapplication.android.pixelpal.databinding.FragmentHomeBinding
import myapplication.android.pixelpal.di.DiContainer
import myapplication.android.pixelpal.ui.delegates.delegates.info_box.InfoBoxDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.info_box.InfoBoxDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.info_box.InfoBoxModel
import myapplication.android.pixelpal.ui.delegates.delegates.news_main.NewsDelegate
import myapplication.android.pixelpal.ui.delegates.delegates.news_main.NewsDelegateItem
import myapplication.android.pixelpal.ui.delegates.delegates.news_main.NewsItemModel
import myapplication.android.pixelpal.ui.home.recycler_view.releases.ReleasesModel
import myapplication.android.pixelpal.ui.delegates.main.MainAdapter
import myapplication.android.pixelpal.ui.home.model.GamesNewsListUi
import myapplication.android.pixelpal.ui.home.mvi.HomeContentResult
import myapplication.android.pixelpal.ui.home.mvi.HomeEffect
import myapplication.android.pixelpal.ui.home.mvi.HomeIntent
import myapplication.android.pixelpal.ui.home.mvi.HomePartialState
import myapplication.android.pixelpal.ui.home.mvi.HomeState
import myapplication.android.pixelpal.ui.home.mvi.HomeStore
import myapplication.android.pixelpal.ui.listener.ClickListener
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviBaseFragment
import java.util.Date


class HomeFragment :
    MviBaseFragment<
            HomePartialState,
            HomeIntent,
            HomeState,
            HomeEffect
            >(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override val store: HomeStore by viewModels { DiContainer.homeStoreFactory }

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
            is LceState.Content -> {
                binding.progressBar.root.visibility = GONE
                initRecycler(state.ui.data)
            }

            is LceState.Error -> {
                binding.progressBar.root.visibility = GONE
                Log.i(TAG, "${state.ui.throwable.message}")
            }

            LceState.Loading -> binding.progressBar.root.visibility = VISIBLE
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
        val adapter = initAdapter()

        val released = addReleaseItems(gamesNews.gamesReleased)
        val releaseThisMonth = addReleaseItems(gamesNews.gameMonthReleases)
        val topGames = addReleaseItems(gamesNews.gamesTop)

        adapter.submitList(getMainItems(released, releaseThisMonth, topGames))
    }

    private fun initAdapter(): MainAdapter {
        val adapter = MainAdapter()
        adapter.addDelegate(NewsDelegate())
        adapter.addDelegate(InfoBoxDelegate())
        binding.recycler.adapter = adapter
        return adapter
    }

    private fun getMainItems(
        released: List<ReleasesModel>,
        releaseThisMonth: List<ReleasesModel>,
        topGames: List<ReleasesModel>
    ) = listOf(
        NewsDelegateItem(
            NewsItemModel(
                1,
                getString(R.string.new_releases),
                getString(R.string.there_is_no_new_games_released_this_month),
                released,
                object : ClickListener {
                    override fun onClick() {
                        TODO("open all new releases")
                    }
                }
            )
        ),
        NewsDelegateItem(
            NewsItemModel(
                2,
                getString(R.string.release_this_month),
                getString(R.string.there_is_no_games_releases_this_month),
                releaseThisMonth,
                object : ClickListener {
                    override fun onClick() {
                        TODO("Open all games releases in this month")
                    }
                }
            )
        ),
        InfoBoxDelegateItem(
            InfoBoxModel(
                3,
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
                3,
                getString(R.string.all_time_top),
                "",
                topGames,
                object : ClickListener {
                    override fun onClick() {
                        TODO("open all top games")
                    }
                }
            )
        )
    )

    private fun addReleaseItems(list: GamesNewsListUi): List<ReleasesModel> {
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "HomeFragmentException"
    }

}