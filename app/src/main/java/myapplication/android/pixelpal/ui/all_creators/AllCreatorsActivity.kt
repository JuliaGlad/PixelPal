package myapplication.android.pixelpal.ui.all_creators

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import myapplication.android.pixelpal.app.Constants
import myapplication.android.pixelpal.databinding.ActivityAllCreatorsBinding
import myapplication.android.pixelpal.ui.creator_details.CreatorDetailsActivity

class AllCreatorsActivity : AppCompatActivity() {

    private var _binding: ActivityAllCreatorsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAllCreatorsBinding.inflate(layoutInflater)
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
            putExtra(Constants.CREATOR_ID, creatorId)
            putExtra(Constants.CREATOR_NAME, name)
            putExtra(Constants.CREATOR_ROLE, role)
            putExtra(Constants.CREATOR_FAMOUS_PROJECTS, famousProjects)
            putExtra(Constants.CREATOR_IMAGE, image)
        }
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}