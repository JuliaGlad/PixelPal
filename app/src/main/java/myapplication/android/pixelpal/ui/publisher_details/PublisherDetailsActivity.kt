package myapplication.android.pixelpal.ui.publisher_details

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.databinding.ActivityPublisherDetailsBinding
import myapplication.android.pixelpal.ui.all_games.AllGamesActivity
import myapplication.android.pixelpal.ui.game_details.activity.GameDetailsActivity

class PublisherDetailsActivity : AppCompatActivity() {

    private var _binding: ActivityPublisherDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPublisherDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun openAllPublisherActivity(publisherId: Long) {
        val intent = Intent(this, AllGamesActivity::class.java).apply {
            putExtra(ALL_INTENT_ID, PUBLISHER_GAMES_ID)
            putExtra(PUBLISHER_ID, publisherId)
        }
        startActivity(intent)
    }

    fun openGameDetailsActivity(
        gameId: Long,
        name: String,
        genres: String,
        released: String,
        image: String
    ) {
        val intent = Intent(this, GameDetailsActivity::class.java).apply {
            putExtra(GAME_ID_ARG, gameId)
            putExtra(GAME_NAME_ARG, name)
            putExtra(GAME_GENRES_ARG, genres)
            putExtra(GAME_RELEASE_ARG, released)
            putExtra(GAME_IMAGE_ARG, image)
        }
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        const val ALL_INTENT_ID = "AllIntentId"
        const val PUBLISHER_ID = "PublisherId"
        const val PUBLISHER_GAMES_ID = "PublisherGamesId"
        const val GAME_ID_ARG = "gameIdArg"
        const val GAME_NAME_ARG = "gameNameArg"
        const val GAME_RELEASE_ARG = "gameReleaseArg"
        const val GAME_GENRES_ARG = "gameGenresArg"
        const val GAME_IMAGE_ARG = "gameImageArg"
    }

}