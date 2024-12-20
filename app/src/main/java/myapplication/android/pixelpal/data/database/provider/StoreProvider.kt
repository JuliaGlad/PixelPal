package myapplication.android.pixelpal.data.database.provider

import myapplication.android.pixelpal.app.App.Companion.app
import myapplication.android.pixelpal.data.database.entities.PublisherEntity
import myapplication.android.pixelpal.data.database.entities.StoreEntity
import myapplication.android.pixelpal.data.models.publishers.PublishersList
import myapplication.android.pixelpal.data.models.stores.StoresList

class StoreProvider {

    fun getStores(): List<StoreEntity> =
        app.database.storeDao().getAll()

    fun deleteStores() { app.database.storeDao().deleteAll() }

    fun insertStores(stores: StoresList) {
        val entities = mutableListOf<StoreEntity>()
        for (i in stores.items){
            with(i){
                entities.add(
                    StoreEntity(id, image, domain, name, projects)
                )
            }
        }
        app.database.storeDao().insertAll(entities)
    }

}