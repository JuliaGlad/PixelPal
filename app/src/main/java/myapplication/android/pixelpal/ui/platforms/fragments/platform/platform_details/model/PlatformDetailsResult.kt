package myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.model

import myapplication.android.pixelpal.ui.home.model.GamesNewsListUi

class PlatformDetailsResult(
    val platformDetailsUi: PlatformDetailsUi,
    val games: GamesNewsListUi,
    var newItems: GamesNewsListUi? = null
)