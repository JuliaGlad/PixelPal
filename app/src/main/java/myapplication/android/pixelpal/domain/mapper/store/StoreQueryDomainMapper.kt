package myapplication.android.pixelpal.domain.mapper.store

import myapplication.android.pixelpal.data.repository.dto.store.StoreDtoList
import myapplication.android.pixelpal.domain.model.stores.StoreDomain
import myapplication.android.pixelpal.domain.model.stores.StoreDomainList
import java.util.stream.Collectors

fun StoreDtoList.toDomain(query: String) =
    StoreDomainList(
        stores.stream()
            .filter { it.name.lowercase().contains(query.lowercase()) }
            .map { with(it){
                StoreDomain(
                    id = id,
                    name = name,
                    image = image,
                    domain = domain,
                    projects = projects
                )
            }
            }.collect(Collectors.toList())
    )
