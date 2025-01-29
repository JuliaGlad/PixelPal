package myapplication.android.pixelpal.domain.mapper.publishers

import android.text.Html
import androidx.core.text.HtmlCompat
import myapplication.android.pixelpal.data.repository.dto.publisher.PublisherDetailsDto
import myapplication.android.pixelpal.domain.model.publishers.PublisherDomainDetails

fun PublisherDetailsDto.toDomain() =
    PublisherDomainDetails(Html.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString())