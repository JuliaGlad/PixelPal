package myapplication.android.pixelpal.data.database.provider

import myapplication.android.pixelpal.app.App.Companion.app
import myapplication.android.pixelpal.data.database.entities.PlatformEntity
import myapplication.android.pixelpal.data.models.platforms.PlatformsList

class PlatformProvider {

    fun getPlatforms(): List<PlatformEntity> =
        app.database.platformDao().getAll()

    fun deletePlatforms() { app.database.platformDao().deleteAll() }

    fun insertPlatforms(platforms: PlatformsList) {
        val entities = mutableListOf<PlatformEntity>()
        for (i in platforms.items){
            with(i){
                entities.add(
                    PlatformEntity(id, name, image, gamesCount, startYear)
                )
            }
        }
        app.database.platformDao().insertAll(entities)
    }

}