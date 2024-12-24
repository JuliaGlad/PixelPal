package myapplication.android.pixelpal.data.source.stores

import myapplication.android.pixelpal.data.models.stores.StoresList

interface StoresLocalSource {

    fun getStores(page: Int) : StoresList?

    fun deleteStores()

    fun insertStores(currentPage: Int, stores: StoresList)
}