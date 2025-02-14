package myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.model

import myapplication.android.pixelpal.ui.home.model.GamesMainInfoListUi

class PlatformDetailsResult(
    val platformDetailsUi: PlatformDetailsUi,
    val games: GamesMainInfoListUi,
    var newItems: GamesMainInfoListUi? = null
)