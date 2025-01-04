package myapplication.android.pixelpal.ui.game_details.model.wrapper

import myapplication.android.pixelpal.domain.model.stores.StoreSellingGameLinksDomainList
import myapplication.android.pixelpal.ui.game_details.model.StoreLinksUi
import myapplication.android.pixelpal.ui.game_details.model.StoresSellingGameUiList
import java.util.stream.Collectors

fun StoreSellingGameLinksDomainList.toUi() =
    StoresSellingGameUiList(
        items.stream()
            .map {
                StoreLinksUi(
                    it.id,
                    it.url
                )
            }.collect(Collectors.toList())
    )