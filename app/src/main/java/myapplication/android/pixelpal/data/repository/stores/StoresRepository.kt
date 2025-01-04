package myapplication.android.pixelpal.data.repository.stores

import myapplication.android.pixelpal.domain.model.stores.StoreDomainList
import myapplication.android.pixelpal.domain.model.stores.StoreSellingGameLinksDomainList

interface StoresRepository {

    suspend fun getStoresSellingGameLinks(gameId: String, page: Int): StoreSellingGameLinksDomainList

    suspend fun getStores(page: Int) : StoreDomainList

}