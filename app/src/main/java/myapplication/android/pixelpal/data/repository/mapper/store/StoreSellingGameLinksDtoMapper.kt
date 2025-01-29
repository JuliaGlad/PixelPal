package myapplication.android.pixelpal.data.repository.mapper.store

import myapplication.android.pixelpal.data.models.stores.store_link.StoreLink
import myapplication.android.pixelpal.data.models.stores.store_link.StoreLinksList
import myapplication.android.pixelpal.data.repository.dto.store.StoreLinkDto
import myapplication.android.pixelpal.data.repository.dto.store.StoresSellingGameLinksDtoList
import java.util.stream.Collectors

fun StoreLinksList.toDto() =
    StoresSellingGameLinksDtoList(
        items.stream()
            .map { it.toDto() }
            .collect(Collectors.toList())
    )

fun StoreLink.toDto() =
    StoreLinkDto(
        id = storeId,
        url = url
    )