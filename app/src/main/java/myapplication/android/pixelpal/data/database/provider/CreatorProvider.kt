package myapplication.android.pixelpal.data.database.provider

import android.util.Log
import myapplication.android.pixelpal.app.App.Companion.app
import myapplication.android.pixelpal.data.database.entities.CreatorEntity
import myapplication.android.pixelpal.data.models.creators.CreatorsList

class CreatorProvider {

    fun getCreators(): List<CreatorEntity> = app.database.creatorsDao().getAll()

    fun deleteCreators() {
        app.database.creatorsDao().deleteAll()
    }

    fun insertCreators(creators: CreatorsList) {
        val entities = mutableListOf<CreatorEntity>()
        for (i in creators.items!!) {
            with(i) {
                entities.add(CreatorEntity(id, name, role, gamesCount, image))
            }
        }
        app.database.creatorsDao().insertAll(entities)
    }
}