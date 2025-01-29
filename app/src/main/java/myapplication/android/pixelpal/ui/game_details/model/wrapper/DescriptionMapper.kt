package myapplication.android.pixelpal.ui.game_details.model.wrapper

import myapplication.android.pixelpal.domain.model.games.GameDescriptionDomain
import myapplication.android.pixelpal.ui.game_details.model.GameDescriptionUi

fun GameDescriptionDomain.toUi() = GameDescriptionUi(description)