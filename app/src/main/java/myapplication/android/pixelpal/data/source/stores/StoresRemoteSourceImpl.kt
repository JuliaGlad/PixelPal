package myapplication.android.pixelpal.data.source.stores

import myapplication.android.pixelpal.data.api.GamesApi
import myapplication.android.pixelpal.data.models.stores.store.StoresList
import myapplication.android.pixelpal.data.models.stores.store_link.StoreLinksList
import javax.inject.Inject

class StoresRemoteSourceImpl @Inject constructor(
    private val api: GamesApi
): StoresRemoteSource {
    override suspend fun getStoresSellingGame(gameId: String, page: Int): StoreLinksList =
        api.getStoresSellingGame(gameId, page)

    override suspend fun getStores(page: Int): StoresList =
        api.getStores(page)
}