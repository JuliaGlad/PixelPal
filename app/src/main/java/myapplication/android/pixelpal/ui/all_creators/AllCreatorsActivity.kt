package myapplication.android.pixelpal.ui.all_creators

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
            putExtra(CREATOR_ID, creatorId)
            putExtra(CREATOR_NAME, name)
            putExtra(CREATOR_ROLE, role)
            putExtra(CREATOR_FAMOUS_PROJECTS, famousProjects)
            putExtra(CREATOR_IMAGE, image)
        }
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        const val CREATOR_ID = "CreatorId"
        const val CREATOR_NAME = "CreatorName"
        const val CREATOR_ROLE = "CreatorRoles"
        const val CREATOR_FAMOUS_PROJECTS = "CreatorFamousProjects"
        const val CREATOR_IMAGE = "CreatorImage"
    }

}