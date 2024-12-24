package myapplication.android.pixelpal.data.database.provider

import myapplication.android.pixelpal.app.App.Companion.app
import myapplication.android.pixelpal.data.database.entities.PublisherEntity
import myapplication.android.pixelpal.data.models.publishers.PublishersList

class PublisherProvider {

    fun getPublishers(page: Int): List<PublisherEntity> {
        val data = app.database.publisherDao().getAll()
        val result = mutableListOf<PublisherEntity>()
        if (data.isNotEmpty()) {
            for (i in data){
                if (i.page == page){
                    result.add(i)
                }
            }
        }
        return result
    }

    fun deletePublishers() {
        app.database.publisherDao().deleteAll()
    }

    fun insertPublishers(currentPage: Int, publishers: PublishersList) {
        val entities = mutableListOf<PublisherEntity>()
        for (i in publishers.items) {
            with(i) {
                entities.add(
                    PublisherEntity(
                        id,
                        currentPage,
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