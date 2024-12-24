package myapplication.android.pixelpal.data.source.stores

import myapplication.android.pixelpal.data.api.GamesApi
import myapplication.android.pixelpal.data.models.stores.StoresList
import javax.inject.Inject

class StoresRemoteSourceImpl @Inject constructor(
    private val api: GamesApi
): StoresRemoteSource {
    override suspend fun getStores(page: Int): StoresList =
        api.getStores(page)
}