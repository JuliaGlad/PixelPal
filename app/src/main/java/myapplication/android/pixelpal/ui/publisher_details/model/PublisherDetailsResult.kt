package myapplication.android.pixelpal.ui.publisher_details.model

import myapplication.android.pixelpal.ui.home.model.GamesNewsListUi

class PublisherDetailsResult(
    val publisherDetails: PublisherDetailsModelUi,
    val games: GamesNewsListUi,
    var newItems: GamesNewsListUi? = null
)