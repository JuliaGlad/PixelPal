package myapplication.android.pixelpal.ui.home.mvi

import myapplication.android.pixelpal.ui.home.model.GamesNewsListUi

class HomeContentResult(
    val gamesTop: GamesNewsListUi,
    val gamesReleased: GamesNewsListUi,
    val gameMonthReleases: GamesNewsListUi
)