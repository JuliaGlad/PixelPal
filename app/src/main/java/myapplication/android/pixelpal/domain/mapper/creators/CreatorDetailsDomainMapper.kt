package myapplication.android.pixelpal.domain.mapper.creators

import android.text.Html
import androidx.core.text.HtmlCompat
import myapplication.android.pixelpal.data.repository.dto.creators.CreatorDetailsDto
import myapplication.android.pixelpal.domain.model.creator.CreatorDetailsDomain

fun CreatorDetailsDto.toDomain() =
    CreatorDetailsDomain(
        description = Html.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString(),
        rating = rating
    )