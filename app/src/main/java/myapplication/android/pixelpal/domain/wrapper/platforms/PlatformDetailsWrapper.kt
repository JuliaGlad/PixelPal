package myapplication.android.pixelpal.domain.wrapper.platforms

import android.text.Html
import androidx.core.text.HtmlCompat
import myapplication.android.pixelpal.data.models.platforms.PlatformDetails
import myapplication.android.pixelpal.domain.model.platform.PlatformDomainDetails

fun PlatformDetails.toDomain() =
    PlatformDomainDetails(
        yearEnd,  Html.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    )