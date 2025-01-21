package myapplication.android.pixelpal.ui.all_creators

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.app.App.Companion.appComponent
import myapplication.android.pixelpal.app.Constants
import myapplication.android.pixelpal.ui.creator_details.CreatorDetailsActivity

class AllCreatorsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.allCreatorsActivityComponent()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_all_creators)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
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

}