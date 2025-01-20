package myapplication.android.pixelpal.ui.game_details

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.app.App.Companion.appComponent
import myapplication.android.pixelpal.app.Constants
import myapplication.android.pixelpal.ui.all_games.AllGamesActivity

class GameDetailsActivity : AppCompatActivity() {

    private val gameDetailsActivityComponent by lazy {
        appComponent.gameDetailsActivityComponent().create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameDetailsActivityComponent.inject(this)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_details)
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
    fun openAllGamesActivity(
        intentId: Int,
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