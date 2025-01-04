package myapplication.android.pixelpal.domain.wrapper.store

import myapplication.android.pixelpal.data.models.stores.store_link.StoreLinksList
import myapplication.android.pixelpal.domain.model.stores.StoreLinkDomain
import myapplication.android.pixelpal.domain.model.stores.StoreSellingGameLinksDomainList
import java.util.stream.Collectors

fun StoreLinksList.toDomain() =
    StoreSellingGameLinksDomainList(
        items.stream()
            .map {
                with(it) {
                    StoreLinkDomain(
                        storeId,
                        url
                    )
                }
            }.collect(Collectors.toList())
    )