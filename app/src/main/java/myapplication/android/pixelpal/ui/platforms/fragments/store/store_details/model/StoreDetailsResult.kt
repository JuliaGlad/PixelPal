package myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.model

import myapplication.android.pixelpal.ui.home.model.GamesMainInfoListUi

class StoreDetailsResult(
    val storeDetailsUi: StoreDetailsUi,
    val games: GamesMainInfoListUi,
    var newItems: GamesMainInfoListUi? = null
)