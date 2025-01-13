package myapplication.android.pixelpal.domain.wrapper.store

import android.text.Html
import androidx.core.text.HtmlCompat
import myapplication.android.pixelpal.data.models.stores.StoreDetails
import myapplication.android.pixelpal.domain.model.stores.StoreDetailsDomain

fun StoreDetails.toDomain() =
    StoreDetailsDomain(Html.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString())