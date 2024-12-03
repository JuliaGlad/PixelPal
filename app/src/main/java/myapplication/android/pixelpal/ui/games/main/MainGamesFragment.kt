package myapplication.android.pixelpal.ui.games.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.app.App
import myapplication.android.pixelpal.databinding.FragmentMainGamesBinding
import myapplication.android.pixelpal.ui.games.games.GamesFragment
import myapplication.android.pixelpal.ui.games.games.recycler_view.LayoutType
import myapplication.android.pixelpal.ui.games.main.mvi.MainGamesEffects
import myapplication.android.pixelpal.ui.games.main.mvi.MainGamesIntent
import myapplication.android.pixelpal.ui.games.main.mvi.MainGamesLocalDI
import myapplication.android.pixelpal.ui.games.main.mvi.MainGamesPartialState
import myapplication.android.pixelpal.ui.games.main.mvi.MainGamesState
import myapplication.android.pixelpal.ui.games.main.mvi.MainGamesStore
import myapplication.android.pixelpal.ui.games.main.mvi.MainGamesStoreFactory
import myapplication.android.pixelpal.ui.games.model.GenreUi
import myapplication.android.pixelpal.ui.games.model.GenresUiList
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviBaseFragment
import myapplication.android.pixelpal.ui.platforms.pager.PagerAdapter
import javax.inject.Inject


class MainGamesFragment : MviBaseFragment<
        MainGamesPartialState,
        MainGamesIntent,
        MainGamesState,
        MainGamesEffects>(R.layout.fragment_main_games) {
    private val mainGamesComponent by lazy {
        (activity?.application as App).appComponent.mainGamesComponent().create()
    }
    private var chosenId: Long = ALL_ID
    private var _binding: FragmentMainGamesBinding? = null
    private var layoutType: LayoutType = LayoutType.Grid
    private val binding get() = _binding!!
    private val instances = mutableListOf<GamesFragment>()
    private lateinit var genres: GenresUiList
    private val pagerAdapter by lazy { PagerAdapter(childFragmentManager, lifecycle) }

    @Inject
    lateinit var mainGamesLocalDI: MainGamesLocalDI

    private val viewModel: MainGamesViewModel by viewModels {
        MainGamesViewModel.Factory(
            mainGamesComponent.mainGamesViewModelFactory()
        )
    }

    override val store: MainGamesStore by viewModels {
        MainGamesStoreFactory(mainGamesLocalDI.reducer, mainGamesLocalDI.actor)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainGamesComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainGamesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            store.sendIntent(MainGamesIntent.Init)
        }
        store.sendIntent(MainGamesIntent.GetGenres)
        collectDescription()
        initLayoutType()
    }

    override fun resolveEffect(effect: MainGamesEffects) {
        TODO("handle effects")
    }

    override fun render(state: MainGamesState) {
        when (state.ui) {
            is LceState.Content -> {
                val genresList = state.ui.data
                this.genres = genresList
                initPager()
                initTabs()
                binding.loadingMain.root.visibility = GONE
            }

            is LceState.Error -> {
                binding.loadingMain.root.visibility = GONE
                Log.e("Error main games", state.ui.throwable.message.toString())
            }

            LceState.Loading -> binding.loadingMain.root.visibility = VISIBLE
        }
    }

    private fun initLayoutType() {
        binding.iconSort.setOnClickListener {
            when (layoutType) {
                LayoutType.Grid -> {
                    setLayoutType(LayoutType.Linear, R.drawable.ic_linear_items)
                }

                LayoutType.Linear -> {
                    setLayoutType(LayoutType.OneItem, R.drawable.ic_one_item)
                }

                LayoutType.OneItem -> {
                    setLayoutType(LayoutType.Grid, R.drawable.ic_grid_items)
                }
            }
        }
    }

    private fun setLayoutType(layoutType: LayoutType, drawable: Int) {
        this.layoutType = layoutType
        ResourcesCompat.getDrawable(
            resources,
            drawable,
            context?.theme
        )?.let {
            binding.iconSort.setIcon(
                it
            )
        }
        var chosenIndex = 0
        for (i in genres.items) {
            val currentIndex = genres.items.indexOf(i)
            instances[currentIndex].setLayoutType(layoutType)
            if (chosenId != ALL_ID && chosenId == i.id) chosenIndex = currentIndex + 1
            pagerAdapter.notifyItemChanged(currentIndex)
        }

        instances[chosenIndex].updateLayoutRecycler()
    }

    private fun initTabs() {
        val tabs = mutableListOf(GenreUi(ALL_ID, getString(R.string.all)))
        genres.items.forEach { tabs.add(it) }

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.id = tabs[position].id.toInt()
            tab.text = tabs[position].title
        }.attach()
        binding.infoBox.root.visibility = GONE

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.id?.toLong()?.let {
                    if (it != ALL_ID) {
                        binding.infoBox.root.visibility = VISIBLE
                        binding.infoBox.loading.visibility = VISIBLE
                        viewModel.getGenreDescription(it)
                    } else binding.infoBox.root.visibility = GONE
                    chosenId = it
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.i("TabUnselected", tab?.id.toString())
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.i("TabReselected", tab?.id.toString())
            }
        })
    }

    private fun initPager() {
        binding.viewPager.adapter = pagerAdapter
        initPagerFragments(LayoutType.Grid)
    }

    private fun initPagerFragments(layoutType: LayoutType) {
        instances.clear()
        for (i in genres.items) {
            with(i) {
                instances.add(
                    GamesFragment.getInstance(id, layoutType)
                )
            }
        }
        pagerAdapter.update(instances)
        //   pagerAdapter.notifyItemChanged(chosenFragmentPosition)
    }

    private fun collectDescription() {
        lifecycleScope.launch {
            viewModel.genre.collect { description ->
                binding.infoBox.description.setShimmerText(description.text)
                binding.infoBox.loading.visibility = GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ALL_ID = 33L
    }

}