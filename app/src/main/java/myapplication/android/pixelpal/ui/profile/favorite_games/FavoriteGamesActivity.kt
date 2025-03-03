package myapplication.android.pixelpal.ui.profile.favorite_games

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import myapplication.android.pixelpal.app.Constants
import myapplication.android.pixelpal.databinding.ActivityFavoriteGamesBinding
import myapplication.android.pixelpal.ui.game_details.activity.GameDetailsActivity

class FavoriteGamesActivity : AppCompatActivity() {

    private var _binding: ActivityFavoriteGamesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteGamesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun openGameDetailsActivity(
        gameId: Long,
        name: String,
        genres: String,
        released: String,
        image: String,
        launcher: ActivityResultLauncher<Intent>
    ) {
        val intent = Intent(this, GameDetailsActivity::class.java).apply {
            putExtra(Constants.GAME_ID_ARG, gameId)
            putExtra(Constants.GAME_NAME_ARG, name)
            putExtra(Constants.GAME_GENRES_ARG, genres)
            putExtra(Constants.GAME_RELEASE_ARG, released)
            putExtra(Constants.GAME_IMAGE_ARG, image)
        }
        launcher.launch(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}