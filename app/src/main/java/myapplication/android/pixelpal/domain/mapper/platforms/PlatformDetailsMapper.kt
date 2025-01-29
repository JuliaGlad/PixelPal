package myapplication.android.pixelpal.domain.mapper.platforms

import android.text.Html
import androidx.core.text.HtmlCompat
import myapplication.android.pixelpal.data.repository.dto.platforms.PlatformDtoDetails
import myapplication.android.pixelpal.domain.model.platform.PlatformDomainDetails

fun PlatformDtoDetails.toDomain() =
    PlatformDomainDetails(
        endYear,  Html.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    )