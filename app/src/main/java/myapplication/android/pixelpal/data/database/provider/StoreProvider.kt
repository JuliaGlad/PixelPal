package myapplication.android.pixelpal.data.database.provider

import myapplication.android.pixelpal.App.Companion.app
import myapplication.android.pixelpal.data.database.entities.StoreEntity
import myapplication.android.pixelpal.data.models.stores.store.StoresList

class StoreProvider {

    fun getStores(page: Int): List<StoreEntity> {
        val data = app.database.storeDao().getAll()
        val result = mutableListOf<StoreEntity>()
        for (i in data) {
            if (i.page == page) {
                result.add(i)
            }
        }
        return result
    }

    fun deleteStores() {
        app.database.storeDao().deleteAll()
    }

    fun insertStores(currentPage: Int, stores: StoresList) {
        val entities = mutableListOf<StoreEntity>()
        for (i in stores.items) {
            with(i) {
                entities.add(
                    StoreEntity(id, currentPage, image, domain, name, projects)
                )
            }
        }
        app.database.storeDao().insertAll(entities)
    }

}