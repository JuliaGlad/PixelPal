package myapplication.android.pixelpal.data.repository.creators

import myapplication.android.pixelpal.data.models.creators.CreatorsList
import myapplication.android.pixelpal.data.models.creators_roles.RolesList
import myapplication.android.pixelpal.data.source.creators.CreatorsLocalSource
import myapplication.android.pixelpal.data.source.creators.CreatorsRemoteSource
import myapplication.android.pixelpal.domain.model.creator.CreatorDomainList
import myapplication.android.pixelpal.domain.model.creator.RoleDomain
import myapplication.android.pixelpal.domain.wrapper.creators.toDomain
import javax.inject.Inject

class CreatorsRepositoryImpl @Inject constructor(
    private val localSource: CreatorsLocalSource,
    private val remoteSource: CreatorsRemoteSource
) : CreatorsRepository {
    override suspend fun getCreatorsRoles() : List<RoleDomain> =
        (getLocalCreatorsRoles() ?: remoteSource.getCreatorsRoles()).toDomain()

    override suspend fun getCreators(roleId: Int) : CreatorDomainList =
        (getLocalCreators() ?: remoteSource.getCreators()).toDomain(roleId)

    override fun getLocalCreators(): CreatorsList? = null

    override fun getLocalCreatorsRoles(): RolesList? = null

}