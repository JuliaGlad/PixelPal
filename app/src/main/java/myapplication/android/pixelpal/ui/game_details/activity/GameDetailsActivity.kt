package myapplication.android.pixelpal.ui.game_details.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.app.App.Companion.app
import myapplication.android.pixelpal.app.App.Companion.appComponent
import myapplication.android.pixelpal.app.Constants
import myapplication.android.pixelpal.ui.all_creators.AllCreatorsActivity
import myapplication.android.pixelpal.ui.all_games.AllGamesActivity
import myapplication.android.pixelpal.ui.creator_details.CreatorDetailsActivity

class GameDetailsActivity : AppCompatActivity() {

    private val navigator = AppNavigator(this, R.id.game_details_container)
    val presenter by lazy { GameDetailsPresenter(app.router) }
    private val navigationHolder: NavigatorHolder by lazy { app.navigatorHolder }
    private val gameDetailsActivityComponent by lazy {
        appComponent.gameDetailsActivityComponent().create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameDetailsActivityComponent.inject(this)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_details)
        if (savedInstanceState == null){
            presenter.setupRootFragment(GameDetailsScreen.gameDetails())
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationHolder.setNavigator(navigator)
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

    fun openAllCreatorsActivity(
        gameId: Long
    ) {
        val intent = Intent(this, AllCreatorsActivity::class.java).apply {
            putExtra(Constants.ALL_INTENT_ID, Constants.CREATORS_ID)
            putExtra(Constants.GAME_ID_ARG, gameId)
        }
        startActivity(intent)
    }

    fun openAllGamesActivity(
        intentId: String,
        gameId: Long,
        genre: String
    ) {
        val intent = Intent(this, AllGamesActivity::class.java).apply {
            putExtra(Constants.ALL_INTENT_ID, intentId)
            putExtra(Constants.GAME_ID_ARG, gameId)
            putExtra(Constants.GAME_GENRES_ARG, genre)
        }
        startActivity(intent)
    }
}