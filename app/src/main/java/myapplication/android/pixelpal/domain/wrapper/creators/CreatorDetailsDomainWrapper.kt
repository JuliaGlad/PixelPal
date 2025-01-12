package myapplication.android.pixelpal.domain.wrapper.creators

import android.text.Html
import androidx.core.text.HtmlCompat
import myapplication.android.pixelpal.data.models.creators.CreatorDetails
import myapplication.android.pixelpal.domain.model.creator.CreatorDetailsDomain

fun CreatorDetails.toDomain() =
    CreatorDetailsDomain(
        description = Html.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString(),
        rating = rating
    )