package myapplication.android.pixelpal.data.repository.mapper.store

import myapplication.android.pixelpal.data.models.stores.store.Store
import myapplication.android.pixelpal.data.models.stores.store.StoresList
import myapplication.android.pixelpal.data.repository.dto.store.StoreDto
import myapplication.android.pixelpal.data.repository.dto.store.StoreDtoList
import java.util.stream.Collectors

fun StoresList.toDto() =
    StoreDtoList(
        items.stream()
            .map { it.toDto() }
            .collect(Collectors.toList())
    )

fun Store.toDto() =
    StoreDto(
        id = id,
        name = name,
        image = image,
        domain = domain,
        projects = projects
    )