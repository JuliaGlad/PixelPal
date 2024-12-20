package myapplication.android.pixelpal.data.source.stores

import myapplication.android.pixelpal.data.models.stores.StoresList

interface StoresLocalSource {

    fun getStores() : StoresList?

    fun deleteStores()

    fun insertStores(stores: StoresList)
}