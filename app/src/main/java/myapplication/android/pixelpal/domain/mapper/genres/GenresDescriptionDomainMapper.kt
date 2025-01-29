package myapplication.android.pixelpal.domain.mapper.genres

import android.text.Html
import androidx.core.text.HtmlCompat
import myapplication.android.pixelpal.data.repository.dto.genre.GenreDescriptionDto
import myapplication.android.pixelpal.domain.model.genres.GenreDescriptionDomain

fun GenreDescriptionDto.toDomain(): GenreDescriptionDomain {
    return GenreDescriptionDomain(
        description = Html.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    )
}
