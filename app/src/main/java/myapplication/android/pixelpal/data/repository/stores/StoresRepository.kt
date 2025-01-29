package myapplication.android.pixelpal.data.repository.stores

import myapplication.android.pixelpal.data.repository.dto.store.StoreDetailsDto
import myapplication.android.pixelpal.data.repository.dto.store.StoreDtoList
import myapplication.android.pixelpal.data.repository.dto.store.StoresSellingGameLinksDtoList

interface StoresRepository {

    suspend fun getStoreDetails(id: Int): StoreDetailsDto

    suspend fun getStoresSellingGameLinks(gameId: String, page: Int): StoresSellingGameLinksDtoList

    suspend fun getStores(page: Int) : StoreDtoList

}