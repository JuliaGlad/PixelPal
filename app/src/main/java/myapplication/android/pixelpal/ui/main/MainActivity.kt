package myapplication.android.pixelpal.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.AppNavigator
import myapplication.android.pixelpal.R
import myapplication.android.pixelpal.app.App
import myapplication.android.pixelpal.databinding.ActivityMainBinding
import myapplication.android.pixelpal.ui.main.BottomScreen.creators
import myapplication.android.pixelpal.ui.main.BottomScreen.games
import myapplication.android.pixelpal.ui.main.BottomScreen.home
import myapplication.android.pixelpal.ui.main.BottomScreen.platforms
import myapplication.android.pixelpal.ui.main.BottomScreen.profile


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val navigator = AppNavigator(this, R.id.main_container)
    private val app : App by lazy { (application as App) }
    private val presenter: MainPresenter by lazy {
        MainPresenter(
            app.router
        )
    }
    private val navigationHolder: NavigatorHolder by lazy { app.navigatorHolder }

    override fun onCreate(savedInstanceState: Bundle?) {
      //  (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomBar()
        if (savedInstanceState == null) {
            presenter.setupRootFragment(home())
        }
    }

    private fun initBottomBar() {
        binding.bottomNav.itemIconTintList = null
        binding.bottomNav.setOnItemSelectedListener { item ->
            val screen: Screen? = when (item.itemId) {
                R.id.action_home -> home()
                R.id.action_creators -> creators()
                R.id.action_games -> games()
                R.id.action_platforms -> platforms()
                R.id.action_profile -> profile()
                else -> null
            }
            screen?.let { presenter.navigateTo(it) }
            true
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}