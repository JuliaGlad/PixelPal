package myapplication.android.pixelpal.data.repository.stores

import myapplication.android.pixelpal.data.models.stores.StoresList
import myapplication.android.pixelpal.domain.model.stores.StoreDomainList

interface StoresRepository {

    suspend fun getStores() : StoreDomainList

}