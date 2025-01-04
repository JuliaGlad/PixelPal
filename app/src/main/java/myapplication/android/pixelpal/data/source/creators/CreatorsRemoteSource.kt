package myapplication.android.pixelpal.data.source.creators

import myapplication.android.pixelpal.data.models.creators.CreatorsList
import myapplication.android.pixelpal.data.models.creators_roles.RolesList

interface CreatorsRemoteSource {

    suspend fun getGameCreators(gameId: String, page: Int): CreatorsList

    suspend fun getCreatorsRoles(): RolesList

    suspend fun getCreators(page: Int): CreatorsList
}