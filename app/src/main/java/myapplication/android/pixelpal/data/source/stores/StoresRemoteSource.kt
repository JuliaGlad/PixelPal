package myapplication.android.pixelpal.data.source.stores

import myapplication.android.pixelpal.data.models.stores.store.StoresList
import myapplication.android.pixelpal.data.models.stores.store_link.StoreLinksList

interface StoresRemoteSource {

    suspend fun getStoresSellingGame(gameId: String, page: Int): StoreLinksList

    suspend fun getStores(page: Int): StoresList

}