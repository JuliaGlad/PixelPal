package myapplication.android.pixelpal.ui.game_details

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.app.App.Companion.appComponent

class GameDetailsActivity : AppCompatActivity() {

    private val gameDetailsActivityComponent by lazy {
        appComponent.gameDetailsActivityComponent().create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameDetailsActivityComponent.inject(this)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_details)
    }
}