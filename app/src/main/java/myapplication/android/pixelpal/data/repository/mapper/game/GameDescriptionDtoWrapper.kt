package myapplication.android.pixelpal.data.repository.mapper.game

import myapplication.android.pixelpal.data.models.game_description.GameDescription
import myapplication.android.pixelpal.data.repository.dto.game.GameDescriptionDto

fun GameDescription.toDto() =
    GameDescriptionDto(description)