package myapplication.android.pixelpal.ui.all_games

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import myapplication.android.pixelpal.app.Constants
import myapplication.android.pixelpal.databinding.ActivityAllGamesBinding
import myapplication.android.pixelpal.ui.game_details.activity.GameDetailsActivity

class AllGamesActivity : AppCompatActivity() {

    private var _binding: ActivityAllGamesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAllGamesBinding.inflate(layoutInflater)
        setContentView(binding.root)
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