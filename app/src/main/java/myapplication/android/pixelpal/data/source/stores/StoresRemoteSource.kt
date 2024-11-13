package myapplication.android.pixelpal.data.source.stores

import myapplication.android.pixelpal.data.api.GamesApi
import myapplication.android.pixelpal.data.models.stores.StoresList

class StoresRemoteSource(
    private val api: GamesApi
) {
    suspend fun getStores(): StoresList =
        api.getStores()
}