package myapplication.android.pixelpal.main

import com.github.terrakok.cicerone.androidx.FragmentScreen
import myapplication.android.pixelpal.creators.CreatorsFragment
import myapplication.android.pixelpal.games.GamesFragment
import myapplication.android.pixelpal.home.HomeFragment
import myapplication.android.pixelpal.platforms.PlatformsFragment
import myapplication.android.pixelpal.ProfileFragment

object BottomScreen {
    fun home() = FragmentScreen{ HomeFragment() }
    fun creators() = FragmentScreen{ CreatorsFragment() }
    fun profile() = FragmentScreen{ProfileFragment()}
    fun platforms() = FragmentScreen{ PlatformsFragment() }
    fun games() = FragmentScreen{ GamesFragment() }
}