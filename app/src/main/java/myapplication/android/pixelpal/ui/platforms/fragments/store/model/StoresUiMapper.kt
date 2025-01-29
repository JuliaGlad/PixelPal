package myapplication.android.pixelpal.ui.platforms.fragments.store.model

import myapplication.android.pixelpal.domain.model.stores.StoreDomainList
import java.util.stream.Collectors

fun StoreDomainList.toUi() =
    StoresUiList(
        stores.stream()
            .map {
                with(it) {
                    StoreUi(
                        id = id,
                        name = name,
                        image = image,
                        domain = domain,
                        projects = projects
                    )
                }
            }.collect(Collectors.toList())
    )