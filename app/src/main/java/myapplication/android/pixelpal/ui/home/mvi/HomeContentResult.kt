package myapplication.android.pixelpal.ui.home.mvi

import myapplication.android.pixelpal.ui.home.model.GamesNewsUi

class HomeContentResult(
    val gamesTop: List<GamesNewsUi>,
    val gamesReleased: List<GamesNewsUi>,
    val gameMonthReleases: List<GamesNewsUi>
)