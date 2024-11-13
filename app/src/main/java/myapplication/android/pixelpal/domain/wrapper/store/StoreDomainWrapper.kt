package myapplication.android.pixelpal.domain.wrapper.store

import myapplication.android.pixelpal.data.models.stores.StoresList
import myapplication.android.pixelpal.domain.model.stores.StoreDomain
import java.util.stream.Collectors

fun StoresList.toDomain() =
    items.stream()
        .map { with(it){
            StoreDomain(
                id = id,
                name = name,
                image = image,
                domain = domain
            )
        }}.collect(Collectors.toList())