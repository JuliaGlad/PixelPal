package myapplication.android.pixelpal.domain.mapper.store

import android.text.Html
import androidx.core.text.HtmlCompat
import myapplication.android.pixelpal.data.models.stores.StoreDetails
import myapplication.android.pixelpal.data.repository.dto.store.StoreDetailsDto
import myapplication.android.pixelpal.domain.model.stores.StoreDetailsDomain

fun StoreDetailsDto.toDomain() =
    StoreDetailsDomain(Html.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString())