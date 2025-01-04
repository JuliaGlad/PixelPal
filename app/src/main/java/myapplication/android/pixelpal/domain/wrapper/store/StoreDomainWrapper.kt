package myapplication.android.pixelpal.domain.wrapper.store

import myapplication.android.pixelpal.data.models.stores.store.StoresList
import myapplication.android.pixelpal.domain.model.stores.StoreDomain
import myapplication.android.pixelpal.domain.model.stores.StoreDomainList
import java.util.stream.Collectors

fun StoresList.toDomain() =
    StoreDomainList(
        items.stream()
            .map { with(it){
                StoreDomain(
                    id = id,
                    name = name,
                    image = image,
                    domain = domain,
                    projects = projects
                )
            }}.collect(Collectors.toList())
    )
