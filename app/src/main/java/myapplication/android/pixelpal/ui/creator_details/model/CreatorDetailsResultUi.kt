package myapplication.android.pixelpal.ui.creator_details.model

import myapplication.android.pixelpal.ui.home.model.GamesMainInfoListUi

class CreatorDetailsResultUi(
    val creatorDetails: CreatorDetailsUi,
    val games: GamesMainInfoListUi,
    var newItems: GamesMainInfoListUi? = null
)