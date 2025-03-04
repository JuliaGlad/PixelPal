package myapplication.android.pixelpal.ui.game_details.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import myapplication.android.pixelpal.databinding.ActivityGameDetailsBinding
import myapplication.android.pixelpal.ui.all_creators.AllCreatorsActivity
import myapplication.android.pixelpal.ui.all_games.AllGamesActivity
import myapplication.android.pixelpal.ui.creator_details.CreatorDetailsActivity

class GameDetailsActivity : AppCompatActivity() {

    private var _binding: ActivityGameDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGameDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun openCreatorDetailsActivity(
        creatorId: Long,
        name: String,
        role: Array<String?>,
        famousProjects: Int,
        image: String?
    ) {
        val intent = Intent(this, CreatorDetailsActivity::class.java).apply {
            putExtra(CREATOR_ID, creatorId)
            putExtra(CREATOR_NAME, name)
            putExtra(CREATOR_ROLE, role)
            putExtra(CREATOR_FAMOUS_PROJECTS, famousProjects)
            putExtra(CREATOR_IMAGE, image)
        }
        startActivity(intent)
    }

    fun openAllCreatorsActivity(
        gameId: Long
    ) {
        val intent = Intent(this, AllCreatorsActivity::class.java).apply {
            putExtra(ALL_INTENT_ID, CREATORS_ID)
            putExtra(GAME_ID_ARG, gameId)
        }
        startActivity(intent)
    }

    fun openAllGamesActivity(
        intentId: String,
        gameId: Long,
        genre: String
    ) {
        val intent = Intent(this, AllGamesActivity::class.java).apply {
            putExtra(ALL_INTENT_ID, intentId)
            putExtra(GAME_ID_ARG, gameId)
            putExtra(GAME_GENRES_ARG, genre)
        }
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        const val GAME_ID_ARG = "gameIdArg"
        const val GAME_GENRES_ARG = "gameGenresArg"
        const val ALL_INTENT_ID = "AllIntentId"
        const val CREATOR_ID = "CreatorId"
        const val CREATOR_NAME = "CreatorName"
        const val CREATOR_ROLE = "CreatorRoles"
        const val CREATOR_FAMOUS_PROJECTS = "CreatorFamousProjects"
        const val CREATOR_IMAGE = "CreatorImage"
        const val CREATORS_ID = "CreatorId"
    }
}