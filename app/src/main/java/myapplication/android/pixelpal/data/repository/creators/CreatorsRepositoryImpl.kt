package myapplication.android.pixelpal.data.repository.creators

import myapplication.android.pixelpal.data.models.creators.CreatorsList
import myapplication.android.pixelpal.data.models.creators_roles.CreatorsRolesList
import myapplication.android.pixelpal.data.source.creators.CreatorsLocalSource
import myapplication.android.pixelpal.data.source.creators.CreatorsRemoteSource
import myapplication.android.pixelpal.domain.model.creator.CreatorDomain
import myapplication.android.pixelpal.domain.model.creator.CreatorRoleDomain
import myapplication.android.pixelpal.domain.wrapper.creators.toDomain

class CreatorsRepositoryImpl(
    private val localSource: CreatorsLocalSource,
    private val remoteSource: CreatorsRemoteSource
) : CreatorsRepository {
    override suspend fun getCreatorsRoles() : List<CreatorRoleDomain> =
        (getLocalCreatorsRoles() ?: remoteSource.getCreatorsRoles()).toDomain()

    override suspend fun getCreators() : List<CreatorDomain> =
        (getLocalCreators() ?: remoteSource.getCreators()).toDomain()

    override fun getLocalCreators(): CreatorsList? = null

    override fun getLocalCreatorsRoles(): CreatorsRolesList? = null

}