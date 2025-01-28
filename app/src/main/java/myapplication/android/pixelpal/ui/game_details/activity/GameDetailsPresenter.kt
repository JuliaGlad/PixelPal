package myapplication.android.pixelpal.ui.game_details.activity

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen

class GameDetailsPresenter(
    private val router: Router
) {
    fun setupRootFragment(screen: Screen){
        router.newRootScreen(screen)
    }

    fun navigateBack(screen: Screen){
        router.backTo(screen)
    }

    fun navigateTo(screen: Screen){
        router.replaceScreen(screen)
    }
}