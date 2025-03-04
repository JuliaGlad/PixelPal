package myapplication.android.pixelpal.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.AppNavigator
import myapplication.android.pixelpal.App
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.databinding.ActivityMainBinding
import myapplication.android.pixelpal.ui.all_games.AllGamesActivity
import myapplication.android.pixelpal.ui.creator_details.CreatorDetailsActivity
import myapplication.android.pixelpal.ui.game_details.activity.GameDetailsActivity
import myapplication.android.pixelpal.ui.main.BottomScreen.creators
import myapplication.android.pixelpal.ui.main.BottomScreen.games
import myapplication.android.pixelpal.ui.main.BottomScreen.home
import myapplication.android.pixelpal.ui.main.BottomScreen.platforms
import myapplication.android.pixelpal.ui.main.BottomScreen.profile
import myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.PlatformDetailsActivity
import myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.StoreDetailsActivity
import myapplication.android.pixelpal.ui.profile.favorite_games.FavoriteGamesActivity
import myapplication.android.pixelpal.ui.profile.signing.AccountCreationActivity
import myapplication.android.pixelpal.ui.publisher_details.PublisherDetailsActivity


class MainActivity : AppCompatActivity() {
    private val app by lazy { (application as App) }
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val navigator = AppNavigator(this, R.id.main_container)
    val presenter: MainPresenter by lazy {
        MainPresenter(
            app.router
        )
    }
    private val navigationHolder: NavigatorHolder by lazy { app.navigatorHolder }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomBar()
        if (savedInstanceState == null) {
            presenter.setupRootFragment(home())
        }
    }

    fun openFavoritesActivity(){
        val intent = Intent(this, FavoriteGamesActivity::class.java)
        startActivity(intent)
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

    fun launchCreateAccountActivity(launch: ActivityResultLauncher<Intent>){
        val intent = Intent(this, AccountCreationActivity::class.java)
        launch.launch(intent)
    }

    fun openAllTopGamesActivity(
        intentId: String,
        currentDate: String? = null,
        startDate: String? = null,
        endDate: String? = null
    ) {
        val intent = Intent(this, AllGamesActivity::class.java).apply {
            putExtra(ALL_INTENT_ID, intentId)
            currentDate?.let { putExtra(CURRENT_DATE, it) }
            startDate?.let { putExtra(START_DATE, it) }
            endDate?.let { putExtra(END_DATE, it) }
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
            putExtra(CREATOR_ID, creatorId)
            putExtra(CREATOR_NAME, name)
            putExtra(CREATOR_ROLE, role)
            putExtra(CREATOR_FAMOUS_PROJECTS, famousProjects)
            putExtra(CREATOR_IMAGE, image)
        }
        startActivity(intent)
    }

    fun openGameDetailsActivity(gameId: Long, name: String, genres: String, released: String, image: String){
        val intent = Intent(this, GameDetailsActivity::class.java).apply {
            putExtra(GAME_ID_ARG, gameId)
            putExtra(GAME_NAME_ARG, name)
            putExtra(GAME_GENRES_ARG, genres)
            putExtra(GAME_RELEASE_ARG, released)
            putExtra(GAME_IMAGE_ARG, image)
        }
        startActivity(intent)
    }

    fun openPlatformDetailsActivity(
        id: Long,
        name: String,
        gamesCount: Int,
        startYear: Int?,
        background: String
    ){
        val intent = Intent(this, PlatformDetailsActivity::class.java)
            .apply {
                putExtra(PLATFORM_ID, id)
                putExtra(PLATFORM_NAME, name)
                putExtra(PLATFORM_PROJECTS, gamesCount)
                putExtra(PLATFORM_YEAR_START, startYear)
                putExtra(PLATFORM_BACKGROUND, background)
            }
        startActivity(intent)
    }

    fun openPublisherDetailsActivity(
        id: Long,
        name: String,
        gameCount: Int,
        background: String?
    ){
        val intent = Intent(this, PublisherDetailsActivity::class.java)
            .apply {
                putExtra(PUBLISHER_ID, id)
                putExtra(PUBLISHER_NAME, name)
                putExtra(PUBLISHER_GAME_COUNT, gameCount)
                putExtra(PUBLISHER_BACKGROUND, background)
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
                putExtra(STORE_ID, id)
                putExtra(STORE_NAME, name)
                putExtra(STORE_PROJECTS, projects)
                putExtra(STORE_DOMAIN, domain)
                putExtra(STORE_IMAGE, image)
            }
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        const val GAME_ID_ARG = "gameIdArg"
        const val GAME_NAME_ARG = "gameNameArg"
        const val GAME_RELEASE_ARG = "gameReleaseArg"
        const val GAME_GENRES_ARG = "gameGenresArg"
        const val GAME_IMAGE_ARG = "gameImageArg"
        const val ALL_INTENT_ID = "AllIntentId"
        const val END_DATE = "EndDate"
        const val START_DATE = "StartDate"
        const val CURRENT_DATE = "CurrentDate"
        const val PUBLISHER_ID = "PublisherId"
        const val PUBLISHER_NAME = "PublisherName"
        const val PUBLISHER_GAME_COUNT = "PublisherGameCount"
        const val PUBLISHER_BACKGROUND = "PublisherBackground"
        const val PLATFORM_ID = "PlatformId"
        const val PLATFORM_NAME = "PlatformName"
        const val PLATFORM_BACKGROUND = "PlatformBackground"
        const val PLATFORM_YEAR_START = "PlatformYearStart"
        const val PLATFORM_PROJECTS = "PlatformProjects"
        const val STORE_ID = "StoreId"
        const val STORE_NAME = "StoreName"
        const val STORE_DOMAIN = "StoreDomain"
        const val STORE_IMAGE = "StoreImage"
        const val STORE_PROJECTS = "StoreProjects"
        const val CREATOR_ID = "CreatorId"
        const val CREATOR_NAME = "CreatorName"
        const val CREATOR_ROLE = "CreatorRoles"
        const val CREATOR_FAMOUS_PROJECTS = "CreatorFamousProjects"
        const val CREATOR_IMAGE = "CreatorImage"
    }
}