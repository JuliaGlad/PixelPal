package myapplication.android.pixelpal.data.source.creators

import myapplication.android.pixelpal.data.models.creators.CreatorsList
import myapplication.android.pixelpal.data.models.creators_roles.RolesList

interface CreatorsLocalSource{

    fun getCreators(roleId: Int) : CreatorsList?

    fun insertCreators(creators: CreatorsList)

    fun deleteCreators()

    fun getCreatorsRoles(): RolesList?

    fun deleteCreatorsRoles()

    fun insertCreatorsRoles(roles: RolesList)
}