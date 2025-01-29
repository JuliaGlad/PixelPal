package myapplication.android.pixelpal.data.repository.platforms

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import myapplication.android.pixelpal.data.repository.dto.platforms.PlatformDtoDetails
import myapplication.android.pixelpal.data.repository.dto.platforms.PlatformDtoList
import myapplication.android.pixelpal.data.repository.mapper.platform.toDto
import myapplication.android.pixelpal.data.source.platform.PlatformLocalSource
import myapplication.android.pixelpal.data.source.platform.PlatformRemoteSource
import javax.inject.Inject

class PlatformsRepositoryImpl @Inject constructor(
    private val localSource: PlatformLocalSource,
    private val remoteSource: PlatformRemoteSource
) : PlatformsRepository {

    override suspend fun getPlatformDetails(id: Int): PlatformDtoDetails =
       withContext(Dispatchers.IO){
           remoteSource.getPlatformDetails(id).toDto()
       }

    override suspend fun getPlatforms(page: Int): PlatformDtoList {
        val local = localSource.getPlatforms(page)
        val result =
            if (local != null) local
            else {
                val remote = withContext(Dispatchers.IO){
                    remoteSource.getPlatforms(page)
                }
                localSource.insertPlatforms(page, remote)
                remote
            }
        return result.toDto()
    }

}