package myapplication.android.pixelpal.domain.mapper.games

import android.text.Html
import androidx.core.text.HtmlCompat
import myapplication.android.pixelpal.data.repository.dto.game.GameDescriptionDto
import myapplication.android.pixelpal.domain.model.games.GameDescriptionDomain

fun GameDescriptionDto.toDomain() = GameDescriptionDomain(
    Html.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString())