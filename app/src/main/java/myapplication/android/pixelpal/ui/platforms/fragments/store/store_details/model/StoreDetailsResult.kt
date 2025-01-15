package myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.model

import myapplication.android.pixelpal.ui.home.model.GamesNewsListUi

class StoreDetailsResult(
    val storeDetailsUi: StoreDetailsUi,
    val games: GamesNewsListUi,
    var newItems: GamesNewsListUi? = null
)