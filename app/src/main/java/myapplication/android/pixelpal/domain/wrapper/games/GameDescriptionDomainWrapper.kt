package myapplication.android.pixelpal.domain.wrapper.games

import myapplication.android.pixelpal.data.models.game_description.GameDescription
import myapplication.android.pixelpal.domain.model.games.GameDescriptionDomain

fun GameDescription.toDomain() = GameDescriptionDomain(description)