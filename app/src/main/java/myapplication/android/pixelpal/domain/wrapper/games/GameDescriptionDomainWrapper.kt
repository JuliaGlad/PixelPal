package myapplication.android.pixelpal.domain.wrapper.games

import android.text.Html
import androidx.core.text.HtmlCompat
import myapplication.android.pixelpal.data.models.game_description.GameDescription
import myapplication.android.pixelpal.domain.model.games.GameDescriptionDomain

fun GameDescription.toDomain() = GameDescriptionDomain(
    Html.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString())