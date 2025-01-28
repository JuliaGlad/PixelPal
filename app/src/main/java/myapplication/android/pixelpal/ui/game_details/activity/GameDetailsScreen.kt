package myapplication.android.pixelpal.ui.game_details.activity

import com.github.terrakok.cicerone.androidx.FragmentScreen
import myapplication.android.pixelpal.ui.game_details.GameDetailsFragment

object GameDetailsScreen {
    fun gameDetails() = FragmentScreen{ GameDetailsFragment() }
}