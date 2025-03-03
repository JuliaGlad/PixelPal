package myapplication.android.pixelpal.ui.platforms.fragments.store.store_details

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import myapplication.android.pixelpal.app.Constants
import myapplication.android.pixelpal.databinding.ActivityStoreDetailsBinding
import myapplication.android.pixelpal.ui.all_games.AllGamesActivity
import myapplication.android.pixelpal.ui.game_details.activity.GameDetailsActivity

class StoreDetailsActivity : AppCompatActivity() {

    private var _binding: ActivityStoreDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStoreDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun openAllStoresActivity(storeId: Int) {
        val intent = Intent(this, AllGamesActivity::class.java).apply {
            putExtra(Constants.ALL_INTENT_ID, Constants.STORES_GAMES_ID)
            putExtra(Constants.STORE_ID, storeId)
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