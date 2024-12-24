package myapplication.android.pixelpal.data.repository.creators

import myapplication.android.pixelpal.data.repository.getAndCheckData
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

    override suspend fun getCreatorsRoles(): List<RoleDomain> =
        getAndCheckData(
            localSource::getCreatorsRoles,
            remoteSource::getCreatorsRoles,
            localSource::insertCreatorsRoles
        ).toDomain()

    override suspend fun getCreators(page: Int, roleId: Int): CreatorDomainList {
        val local = localSource.getCreators(page, roleId)
        val result =
            if (local != null) local
            else {
                val remote = remoteSource.getCreators(page)
                localSource.insertCreators(page,remote)
                remote
            }.toDomain(roleId)
        return result
    }
}