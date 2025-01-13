package myapplication.android.pixelpal.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.AppNavigator
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.app.App.Companion.app
import myapplication.android.pixelpal.app.App.Companion.appComponent
import myapplication.android.pixelpal.app.Constants
import myapplication.android.pixelpal.databinding.ActivityMainBinding
import myapplication.android.pixelpal.ui.all_games.AllGamesActivity
import myapplication.android.pixelpal.ui.creator_details.CreatorDetailsActivity
import myapplication.android.pixelpal.ui.game_details.GameDetailsActivity
import myapplication.android.pixelpal.ui.main.BottomScreen.creators
import myapplication.android.pixelpal.ui.main.BottomScreen.games
import myapplication.android.pixelpal.ui.main.BottomScreen.home
import myapplication.android.pixelpal.ui.main.BottomScreen.platforms
import myapplication.android.pixelpal.ui.main.BottomScreen.profile
import myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.PlatformDetailsActivity
import myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.StoreDetailsActivity


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val navigator = AppNavigator(this, R.id.main_container)
    private val presenter: MainPresenter by lazy {
        MainPresenter(
            app.router
        )
    }
    private val navigationHolder: NavigatorHolder by lazy { app.navigatorHolder }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.mainActivityComponent().create().inject(this)
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomBar()
        if (savedInstanceState == null) {
            presenter.setupRootFragment(home())
        }
    }

    private fun initBottomBar() {
        binding.bottomNav.itemIconTintList = null
        binding.bottomNav.setOnItemSelectedListener { item ->
            val screen: Screen? = when (item.itemId) {
                R.id.action_home -> home()
                R.id.action_creators -> creators()
                R.id.action_games -> games()
                R.id.action_platforms -> platforms()
                R.id.action_profile -> profile()
                else -> null
            }
            screen?.let { presenter.navigateTo(it) }
            true
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        super.onPause()
    }

    fun openAllTopGamesActivity(
        intentId: Int,
        currentDate: String? = null,
        startDate: String? = null,
        endDate: String? = null
    ) {
        val intent = Intent(this, AllGamesActivity::class.java).apply {
            putExtra(Constants.ALL_INTENT_ID, intentId)
            currentDate?.let { putExtra(Constants.CURRENT_DATE, it) }
            startDate?.let { putExtra(Constants.START_DATE, it) }
            endDate?.let { putExtra(Constants.END_DATE, it) }
        }
        startActivity(intent)
    }

    fun openCreatorDetailsActivity(
        creatorId: Long,
        name: String,
        role: Array<String?>,
        famousProjects: Int,
        image: String?
    ) {
        val intent = Intent(this, CreatorDetailsActivity::class.java).apply {
            putExtra(Constants.CREATOR_ID, creatorId)
            putExtra(Constants.CREATOR_NAME, name)
            putExtra(Constants.CREATOR_ROLE, role)
            putExtra(Constants.CREATOR_FAMOUS_PROJECTS, famousProjects)
            putExtra(Constants.CREATOR_IMAGE, image)
        }
        startActivity(intent)
    }

    fun openGameDetailsActivity(gameId: Long, name: String, genres: String, released: String, image: String){
        val intent = Intent(this, GameDetailsActivity::class.java).apply {
            putExtra(Constants.GAME_ID_ARG, gameId)
            putExtra(Constants.GAME_NAME_ARG, name)
            putExtra(Constants.GAME_GENRES_ARG, genres)
            putExtra(Constants.GAME_RELEASE_ARG, released)
            putExtra(Constants.GAME_IMAGE_ARG, image)
        }
        startActivity(intent)
    }

    fun openPlatformDetailsActivity(
        id: Int,
        name: String,
        gamesCount: Int,
        startYear: Int?,
        background: String
    ){
        val intent = Intent(this, PlatformDetailsActivity::class.java)
            .apply {
                putExtra(Constants.PLATFORM_ID, id)
                putExtra(Constants.PLATFORM_NAME, name)
                putExtra(Constants.PLATFORM_PROJECTS, gamesCount)
                putExtra(Constants.PLATFORM_YEAR_START, startYear)
                putExtra(Constants.PLATFORM_BACKGROUND, background)
            }
        startActivity(intent)
    }

    fun openStoreDetailsActivity(
        id: Int,
        name: String,
        image: String,
        domain: String?,
        projects: Int?
    ){
        val intent = Intent(this, StoreDetailsActivity::class.java)
            .apply {
                putExtra(Constants.STORE_ID, id)
                putExtra(Constants.STORE_NAME, name)
                putExtra(Constants.STORE_PROJECTS, projects)
                putExtra(Constants.STORE_DOMAIN, domain)
                putExtra(Constants.STORE_IMAGE, image)
            }
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}