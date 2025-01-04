package myapplication.android.pixelpal.data.database.provider

import myapplication.android.pixelpal.app.App.Companion.app
import myapplication.android.pixelpal.data.database.entities.CreatorEntity
import myapplication.android.pixelpal.data.models.creators.CreatorsList

class CreatorProvider {
    fun getCreators(page: Int): List<CreatorEntity> {
        val data = app.database.creatorsDao().getAll()
        val result = mutableListOf<CreatorEntity>()
        if (data.isNotEmpty()) {
            for (i in data) {
                with(i) {
                    if (this.page == page) {
                        result.add(this)
                    }
                }
            }
        }
        return result
    }

    fun deleteCreators() {
        app.database.creatorsDao().deleteAll()
    }

    fun insertCreators(creators: CreatorsList, page: Int) {
        val entities = mutableListOf<CreatorEntity>()
        for (i in creators.items!!) {
            with(i) {
                entities.add(CreatorEntity(id, page, name, role, gamesCount, image))
            }
        }
        app.database.creatorsDao().insertAll(entities)
    }
}