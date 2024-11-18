package myapplication.android.pixelpal.ui.home.mvi

import myapplication.android.pixelpal.ui.mvi.MviEffect

open class HomeEffect: MviEffect {

    data class ShowDatesDialog(val date: String): HomeEffect()

}