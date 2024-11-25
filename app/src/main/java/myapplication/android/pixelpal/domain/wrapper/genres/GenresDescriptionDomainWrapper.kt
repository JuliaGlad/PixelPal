package myapplication.android.pixelpal.domain.wrapper.genres

import android.text.Html
import androidx.core.text.HtmlCompat
import myapplication.android.pixelpal.data.models.genres.GenreDescription
import myapplication.android.pixelpal.domain.model.genres.GenreDescriptionDomain

fun GenreDescription.toDomain(): GenreDescriptionDomain {
    return GenreDescriptionDomain(
        description = Html.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    )
}
