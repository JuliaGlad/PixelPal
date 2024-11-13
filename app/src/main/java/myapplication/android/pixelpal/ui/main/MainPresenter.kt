package myapplication.android.pixelpal.ui.main

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen

class MainPresenter(
    private val router: Router
) {

    fun setupRootFragment(screen: Screen){
        router.newRootScreen(screen)
    }

    fun navigateTo(screen: Screen){
        router.replaceScreen(screen)
    }
}