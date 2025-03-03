package myapplication.android.pixelpal.data.database.provider

import myapplication.android.pixelpal.App.Companion.app
import myapplication.android.pixelpal.data.database.entities.CreatorsRolesEntity
import myapplication.android.pixelpal.data.models.creators_roles.RolesList

class CreatorRolesProvider {
    fun getCreatorRoles(): List<CreatorsRolesEntity> =
        app.database.creatorRolesDao().getAll()

    fun deleteCreatorsRoles() { app.database.creatorRolesDao().deleteAll() }

    fun insertCreatorsRoles(roles: RolesList) {
        val entities = mutableListOf<CreatorsRolesEntity>()
        for (i in roles.items){
            with(i){
                entities.add(
                    CreatorsRolesEntity(
                    id,
                    name
                ))
            }
        }
        app.database.creatorRolesDao().insertAll(entities)
    }
}