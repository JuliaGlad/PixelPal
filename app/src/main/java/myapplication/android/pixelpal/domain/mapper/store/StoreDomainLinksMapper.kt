package myapplication.android.pixelpal.domain.mapper.store

import myapplication.android.pixelpal.data.models.stores.store_link.StoreLinksList
import myapplication.android.pixelpal.data.repository.dto.store.StoresSellingGameLinksDtoList
import myapplication.android.pixelpal.domain.model.stores.StoreLinkDomain
import myapplication.android.pixelpal.domain.model.stores.StoreSellingGameLinksDomainList
import java.util.stream.Collectors

fun StoresSellingGameLinksDtoList.toDomain() =
    StoreSellingGameLinksDomainList(
        items.stream()
            .map {
                with(it) {
                    StoreLinkDomain(
                        id,
                        url
                    )
                }
            }.collect(Collectors.toList())
    )