package myapplication.android.pixelpal.data.repository.creators

import myapplication.android.pixelpal.domain.model.creator.CreatorDomainList
import myapplication.android.pixelpal.domain.model.creator.RoleDomain

interface CreatorsRepository {

    suspend fun getCreatorsRoles(): List<RoleDomain>

    suspend fun getCreators(page: Int, roleId: Int): CreatorDomainList
}