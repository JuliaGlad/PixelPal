package myapplication.android.pixelpal.ui.games.main

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.databinding.FragmentMainGamesBinding
import myapplication.android.pixelpal.di.DiContainer
import myapplication.android.pixelpal.ui.games.games.FragmentGames
import myapplication.android.pixelpal.ui.games.main.mvi.MainGamesEffects
import myapplication.android.pixelpal.ui.games.main.mvi.MainGamesIntent
import myapplication.android.pixelpal.ui.games.main.mvi.MainGamesPartialState
import myapplication.android.pixelpal.ui.games.main.mvi.MainGamesState
import myapplication.android.pixelpal.ui.games.main.mvi.MainGamesStore
import myapplication.android.pixelpal.ui.games.model.GenreUi
import myapplication.android.pixelpal.ui.games.model.GenresUiList
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviBaseFragment
import myapplication.android.pixelpal.ui.platforms.pager.PagerAdapter


class MainGamesFragment : MviBaseFragment<
        MainGamesPartialState,
        MainGamesIntent,
        MainGamesState,
        MainGamesEffects>(R.layout.fragment_main_games){
    private var _binding: FragmentMainGamesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainGamesViewModel by viewModels()

    override val store: MainGamesStore by viewModels { DiContainer.mainGamesStoreFactory }

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
        if (savedInstanceState == null){
            store.sendIntent(MainGamesIntent.Init)
        }
        store.sendIntent(MainGamesIntent.GetGenres)
        collectDescription()
    }

    override fun resolveEffect(effect: MainGamesEffects) {
        TODO("handle effects")
    }

    override fun render(state: MainGamesState) {
       when(state.ui){
           is LceState.Content -> {
               val genres = state.ui.data
               initPager(genres)
               initTabs(genres)
               binding.loadingMain.root.visibility = GONE
           }
           is LceState.Error -> {
               binding.loadingMain.root.visibility = GONE
               Log.e("Error main games", state.ui.throwable.message.toString())
           }
           LceState.Loading -> binding.loadingMain.root.visibility = VISIBLE
       }
    }

    private fun initTabs(genres: GenresUiList) {
        val tabs = mutableListOf(GenreUi(ALL_ID, getString(R.string.all)))
        genres.items.forEach { tabs.add(it) }

        TabLayoutMediator(binding.tabs, binding.viewPager){ tab, position ->
            tab.id = tabs[position].id.toInt()
            tab.text = tabs[position].title
        }.attach()
        binding.infoBox.root.visibility = GONE

        binding.tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.i("TabSelected", tab?.id.toString())
                tab?.id?.toLong()?.let {
                    if (it != ALL_ID) {
                        binding.infoBox.root.visibility = VISIBLE
                        binding.infoBox.loading.visibility = VISIBLE
                        viewModel.getGenreDescription(it)
                    } else binding.infoBox.root.visibility = GONE
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

    private fun initPager(genres: GenresUiList) {
        val pagerAdapter = PagerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = pagerAdapter

        val instances = mutableListOf<FragmentGames>()
        for (i in genres.items){
            with(i) {
                instances.add(
                    FragmentGames.getInstance(id, title)
                )
            }
        }

        pagerAdapter.update(instances)
    }

    private fun collectDescription() {
        lifecycleScope.launch {
            viewModel.genre.collect{ description ->
                binding.infoBox.description.setShimmerText(description.text)
                binding.infoBox.loading.visibility = GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        const val ALL_ID = 33L
    }

}