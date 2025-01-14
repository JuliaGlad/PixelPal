package myapplication.android.pixelpal.domain.wrapper.publishers

import android.text.Html
import androidx.core.text.HtmlCompat
import myapplication.android.pixelpal.data.models.publishers.PublisherDetails
import myapplication.android.pixelpal.domain.model.publishers.PublisherDomainDetails

fun PublisherDetails.toDomain() =
    PublisherDomainDetails(Html.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString())