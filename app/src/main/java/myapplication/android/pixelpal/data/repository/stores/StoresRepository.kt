package myapplication.android.pixelpal.data.repository.stores

import myapplication.android.pixelpal.data.models.stores.StoresList
import myapplication.android.pixelpal.domain.model.stores.StoreDomain

interface StoresRepository {

    suspend fun getStores() : List<StoreDomain>

    fun getLocalStores() : StoresList?
}