package myapplication.android.pixelpal.ui.home.mvi

import myapplication.android.pixelpal.ui.home.model.GamesMainInfoListUi

class HomeContentResult(
    val gamesTop: GamesMainInfoListUi,
    val gamesReleased: GamesMainInfoListUi,
    val gameMonthReleases: GamesMainInfoListUi
)