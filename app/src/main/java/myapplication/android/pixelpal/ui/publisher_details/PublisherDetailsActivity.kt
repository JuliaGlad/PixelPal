package myapplication.android.pixelpal.ui.publisher_details

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.app.Constants
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
            putExtra(Constants.ALL_INTENT_ID, Constants.PUBLISHER_GAMES_ID)
            putExtra(Constants.PUBLISHER_ID, publisherId)
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
            putExtra(Constants.GAME_ID_ARG, gameId)
            putExtra(Constants.GAME_NAME_ARG, name)
            putExtra(Constants.GAME_GENRES_ARG, genres)
            putExtra(Constants.GAME_RELEASE_ARG, released)
            putExtra(Constants.GAME_IMAGE_ARG, image)
        }
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}