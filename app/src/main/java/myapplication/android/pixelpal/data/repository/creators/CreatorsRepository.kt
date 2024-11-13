package myapplication.android.pixelpal.data.repository.creators

import myapplication.android.pixelpal.data.models.creators.CreatorsList
import myapplication.android.pixelpal.data.models.creators_roles.CreatorsRolesList
import myapplication.android.pixelpal.domain.model.creator.CreatorDomain
import myapplication.android.pixelpal.domain.model.creator.CreatorRoleDomain

interface CreatorsRepository {

    suspend fun getCreatorsRoles(): List<CreatorRoleDomain>

    suspend fun getCreators(): List<CreatorDomain>

    fun getLocalCreators() : CreatorsList?

    fun getLocalCreatorsRoles() : CreatorsRolesList?
}