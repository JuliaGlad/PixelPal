package myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import myapplication.android.pixelpal.app.Constants
import myapplication.android.pixelpal.databinding.ActivityPlatformDetailsBinding
import myapplication.android.pixelpal.ui.all_games.AllGamesActivity
import myapplication.android.pixelpal.ui.game_details.activity.GameDetailsActivity

class PlatformDetailsActivity : AppCompatActivity() {

    private var _binding: ActivityPlatformDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPlatformDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun openAllGamesActivity(platformId: Long){
        val intent = Intent(this, AllGamesActivity::class.java).apply {
            putExtra(Constants.ALL_INTENT_ID, Constants.PLATFORM_GAMES_ID)
            putExtra(Constants.PLATFORM_ID, platformId)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}