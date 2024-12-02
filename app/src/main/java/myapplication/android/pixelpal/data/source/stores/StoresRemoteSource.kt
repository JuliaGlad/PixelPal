package myapplication.android.pixelpal.data.source.stores

import myapplication.android.pixelpal.data.models.stores.StoresList

interface StoresRemoteSource {

    suspend fun getStores(): StoresList

}