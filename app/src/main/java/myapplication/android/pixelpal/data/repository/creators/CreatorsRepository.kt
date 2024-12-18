package myapplication.android.pixelpal.data.repository.creators

import myapplication.android.pixelpal.data.models.creators.CreatorsList
import myapplication.android.pixelpal.data.models.creators_roles.RolesList
import myapplication.android.pixelpal.domain.model.creator.CreatorDomainList
import myapplication.android.pixelpal.domain.model.creator.RoleDomain

interface CreatorsRepository {

    suspend fun getCreatorsRoles(): List<RoleDomain>

    suspend fun getCreators(roleId: Int): CreatorDomainList

    fun getLocalCreators() : CreatorsList?

    fun getLocalCreatorsRoles() : RolesList?
}