package myapplication.android.pixelpal.ui.publisher_details.model

import myapplication.android.pixelpal.ui.home.model.GamesMainInfoListUi

class PublisherDetailsResult(
    val publisherDetails: PublisherDetailsModelUi,
    val games: GamesMainInfoListUi,
    var newItems: GamesMainInfoListUi? = null
)