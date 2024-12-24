package myapplication.android.pixelpal.data.database.provider

import myapplication.android.pixelpal.app.App.Companion.app
import myapplication.android.pixelpal.data.database.entities.PlatformEntity
import myapplication.android.pixelpal.data.models.platforms.PlatformsList

class PlatformProvider {

    fun getPlatforms(page: Int): List<PlatformEntity> {
        val data = app.database.platformDao().getAll()
        val result = mutableListOf<PlatformEntity>()
        for (i in data){
            if (i.page == page){
                result.add(i)
            }
        }
        return result
    }

    fun deletePlatforms() { app.database.platformDao().deleteAll() }

    fun insertPlatforms(currentPage: Int, platforms: PlatformsList) {
        val entities = mutableListOf<PlatformEntity>()
        for (i in platforms.items){
            with(i){
                entities.add(
                    PlatformEntity(id, currentPage, name, image, gamesCount, startYear)
                )
            }
        }
        app.database.platformDao().insertAll(entities)
    }

}