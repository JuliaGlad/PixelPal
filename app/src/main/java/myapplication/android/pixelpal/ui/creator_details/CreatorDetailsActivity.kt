package myapplication.android.pixelpal.ui.creator_details

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.app.Constants
import myapplication.android.pixelpal.databinding.ActivityAllCreatorsBinding
import myapplication.android.pixelpal.ui.all_games.AllGamesActivity
import myapplication.android.pixelpal.ui.game_details.activity.GameDetailsActivity

class CreatorDetailsActivity : AppCompatActivity() {

    private var _binding: ActivityAllCreatorsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAllCreatorsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun openAllGamesActivity(creatorId: Long){
        val intent = Intent(this, AllGamesActivity::class.java).apply {
            putExtra(Constants.ALL_INTENT_ID, Constants.CREATOR_GAMES_ID)
            putExtra(Constants.CREATOR_ID, creatorId)
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