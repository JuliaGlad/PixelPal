package myapplication.android.pixelpal.data.source.creators

import myapplication.android.pixelpal.data.api.GamesApi
import myapplication.android.pixelpal.data.models.creators.CreatorsList
import myapplication.android.pixelpal.data.models.creators_roles.CreatorsRolesList

class CreatorsRemoteSource(
    private val api: GamesApi,
) {
    suspend fun getCreatorsRoles(): CreatorsRolesList =
        api.getCreatorsRoles()

    suspend fun getCreators(): CreatorsList =
        api.getCreators()
}