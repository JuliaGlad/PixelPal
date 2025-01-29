package myapplication.android.pixelpal.data.repository.creators

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import myapplication.android.pixelpal.data.repository.dto.creators.CreatorDetailsDto
import myapplication.android.pixelpal.data.repository.dto.creators.CreatorRoleDto
import myapplication.android.pixelpal.data.repository.dto.creators.CreatorsDtoList
import myapplication.android.pixelpal.data.repository.getAndCheckData
import myapplication.android.pixelpal.data.repository.mapper.creator.toDto
import myapplication.android.pixelpal.data.source.creators.CreatorsLocalSource
import myapplication.android.pixelpal.data.source.creators.CreatorsRemoteSource
import javax.inject.Inject

class CreatorsRepositoryImpl @Inject constructor(
    private val localSource: CreatorsLocalSource,
    private val remoteSource: CreatorsRemoteSource
) : CreatorsRepository {
    override suspend fun getCreatorDetails(id: Long): CreatorDetailsDto =
        withContext(Dispatchers.IO){
            remoteSource.getCreatorDetails(id).toDto()
        }

    override suspend fun getGameCreators(
        gameId: String,
        page: Int
    ): CreatorsDtoList =
        withContext(Dispatchers.IO){
            remoteSource.getGameCreators(gameId, page).toDto()
        }

    override suspend fun getCreatorsRoles(): List<CreatorRoleDto> =
        getAndCheckData(
            localSource::getCreatorsRoles,
            remoteSource::getCreatorsRoles,
            localSource::insertCreatorsRoles
        ).toDto()

    override suspend fun getCreators(page: Int, roleId: Int): CreatorsDtoList {
        val local = localSource.getCreators(page, roleId)
        val result =
            if (local != null) local
            else {
                val remote = withContext(Dispatchers.IO){
                    remoteSource.getCreators(page)
                }
                localSource.insertCreators(page,remote)
                remote
            }.toDto()
        return result
    }
}