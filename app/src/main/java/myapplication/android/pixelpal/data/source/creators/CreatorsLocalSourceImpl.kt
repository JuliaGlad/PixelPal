package myapplication.android.pixelpal.data.source.creators

import myapplication.android.pixelpal.data.database.entities.CreatorEntity
import myapplication.android.pixelpal.data.database.entities.CreatorsRolesEntity
import myapplication.android.pixelpal.data.database.provider.CreatorProvider
import myapplication.android.pixelpal.data.database.provider.CreatorRolesProvider
import myapplication.android.pixelpal.data.models.creators.Creator
import myapplication.android.pixelpal.data.models.creators.CreatorsList
import myapplication.android.pixelpal.data.models.creators_roles.Role
import myapplication.android.pixelpal.data.models.creators_roles.RolesList
import java.util.stream.Collectors
import javax.inject.Inject

class CreatorsLocalSourceImpl @Inject constructor() : CreatorsLocalSource {
    //TODO("Check ids")
    override fun getCreators(roleId: Int): CreatorsList? {
//        val data = CreatorProvider().getCreators()
//        var containId = false
//        data.first().roles.stream()
//            .forEach { if (it.id == roleId) containId = true }
//
//        val result = if (data.isNotEmpty() && containId) {
//            CreatorsList(
//                data
//                    .stream()
//                    .map { it.toCreator() }
//                    .collect(Collectors.toList())
//            )
//        } else null
//
//        if (data.isNotEmpty() && !containId) CreatorProvider().deleteCreators()
//
//        return result
        val data = CreatorProvider().getCreators()
        return if (data.isNotEmpty()) {
            CreatorsList(
                data
                    . stream ()
                    .map { it.toCreator() }
                    .collect(Collectors.toList())
            )
        } else null
    }

    override fun insertCreators(creators: CreatorsList) {
        CreatorProvider().insertCreators(creators)
    }

    override fun deleteCreators() {
        CreatorProvider().deleteCreators()
    }

    override fun getCreatorsRoles(): RolesList? {
        val data = CreatorRolesProvider().getCreatorRoles()
        return if (data.isNotEmpty()) {
            RolesList(
                data
                    .stream()
                    .map { it.toRole() }
                    .collect(Collectors.toList())
            )
        } else null
    }

    override fun deleteCreatorsRoles() {
        CreatorRolesProvider().deleteCreatorsRoles()
    }

    override fun insertCreatorsRoles(roles: RolesList) {
        CreatorRolesProvider().insertCreatorsRoles(roles)
    }
}

fun CreatorsRolesEntity.toRole() =
    Role(roleId, title)

fun CreatorEntity.toCreator() =
    Creator(gamesCount, creatorId, name, roles, image)