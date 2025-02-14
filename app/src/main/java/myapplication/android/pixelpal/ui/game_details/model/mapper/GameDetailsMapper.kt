package myapplication.android.pixelpal.ui.game_details.model.mapper

import myapplication.android.pixelpal.domain.model.games.GameDetailsDomain
import myapplication.android.pixelpal.ui.game_details.model.GameDescriptionUi

fun GameDetailsDomain.toUi() = GameDescriptionUi(description, isFavorite)