package myapplication.android.pixelpal.data.repository.creators

import myapplication.android.pixelpal.data.repository.dto.creators.CreatorDetailsDto
import myapplication.android.pixelpal.data.repository.dto.creators.CreatorRoleDto
import myapplication.android.pixelpal.data.repository.dto.creators.CreatorsDtoList

interface CreatorsRepository {

    suspend fun getCreatorDetails(id: Long): CreatorDetailsDto

    suspend fun getGameCreators(gameId: String,  page: Int): CreatorsDtoList

    suspend fun getCreatorsRoles(): List<CreatorRoleDto>

    suspend fun getCreators(page: Int, roleId: Int): CreatorsDtoList
}