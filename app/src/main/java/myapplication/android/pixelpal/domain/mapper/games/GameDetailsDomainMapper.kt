package myapplication.android.pixelpal.domain.mapper.games

import android.text.Html
import androidx.core.text.HtmlCompat
import myapplication.android.pixelpal.data.repository.dto.game.GameDetailsDto
import myapplication.android.pixelpal.domain.model.games.GameDetailsDomain

fun GameDetailsDto.toDomain() = GameDetailsDomain(
    Html.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString(),
    isFavorite
)