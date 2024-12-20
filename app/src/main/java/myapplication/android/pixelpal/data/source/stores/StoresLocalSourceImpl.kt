package myapplication.android.pixelpal.data.source.stores

import myapplication.android.pixelpal.data.database.entities.StoreEntity
import myapplication.android.pixelpal.data.database.provider.StoreProvider
import myapplication.android.pixelpal.data.models.stores.Store
import myapplication.android.pixelpal.data.models.stores.StoresList
import java.util.stream.Collectors
import javax.inject.Inject

class StoresLocalSourceImpl @Inject constructor(): StoresLocalSource {
    override fun getStores(): StoresList? {
        val data = StoreProvider().getStores()
        return if (data.isNotEmpty()) {
            StoresList(
                data.stream()
                    .map { it.toStore() }
                    .collect(Collectors.toList())
            )
        } else null
    }

    override fun deleteStores() {
        StoreProvider().deleteStores()
    }

    override fun insertStores(stores: StoresList) {
        StoreProvider().insertStores(stores)
    }

    private fun StoreEntity.toStore() = Store(image, domain, storeId, name, projects)
}