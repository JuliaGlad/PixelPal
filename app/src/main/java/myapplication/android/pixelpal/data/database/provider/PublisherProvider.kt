package myapplication.android.pixelpal.data.database.provider

import myapplication.android.pixelpal.app.App.Companion.app
import myapplication.android.pixelpal.data.database.entities.PublisherEntity
import myapplication.android.pixelpal.data.models.publishers.PublishersList

class PublisherProvider {

    fun getPublishers(): List<PublisherEntity> =
        app.database.publisherDao().getAll()

    fun deletePublishers() { app.database.publisherDao().deleteAll() }

    fun insertPublishers(publishers: PublishersList) {
        val entities = mutableListOf<PublisherEntity>()
        for (i in publishers.items){
            with(i){
                entities.add(
                    PublisherEntity(
                        id,
                        name,
                        gamesCount,
                        image
                    )
                )
            }
        }
        app.database.publisherDao().insertAll(entities)
    }

}