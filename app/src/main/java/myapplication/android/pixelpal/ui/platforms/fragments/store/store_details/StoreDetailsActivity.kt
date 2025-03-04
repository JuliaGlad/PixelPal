package myapplication.android.pixelpal.ui.platforms.fragments.store.store_details

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
            putExtra(ALL_INTENT_ID, STORES_GAMES_ID)
            putExtra(STORE_ID, storeId)
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
        const val GAME_ID_ARG = "gameIdArg"
        const val GAME_NAME_ARG = "gameNameArg"
        const val GAME_RELEASE_ARG = "gameReleaseArg"
        const val GAME_GENRES_ARG = "gameGenresArg"
        const val GAME_IMAGE_ARG = "gameImageArg"
        const val ALL_INTENT_ID = "AllIntentId"
        const val STORE_ID = "StoreId"
        const val STORES_GAMES_ID = "StoreGamesId"
    }

}