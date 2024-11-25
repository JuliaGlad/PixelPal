package myapplication.android.pixelpal.ui.main

import com.github.terrakok.cicerone.androidx.FragmentScreen
import myapplication.android.pixelpal.ui.creators.CreatorsFragment
import myapplication.android.pixelpal.ProfileFragment
import myapplication.android.pixelpal.ui.games.main.MainGamesFragment

object BottomScreen {
    fun home() = FragmentScreen{ myapplication.android.pixelpal.ui.home.HomeFragment() }
    fun creators() = FragmentScreen{ CreatorsFragment() }
    fun profile() = FragmentScreen{ProfileFragment()}
    fun platforms() = FragmentScreen{ myapplication.android.pixelpal.ui.platforms.PlatformsFragment() }
    fun games() = FragmentScreen{ MainGamesFragment() }
}