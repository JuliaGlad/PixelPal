package myapplication.android.pixelpal.data.source.creators

import myapplication.android.pixelpal.data.api.GamesApi
import myapplication.android.pixelpal.data.models.creators.CreatorsList
import myapplication.android.pixelpal.data.models.creators_roles.RolesList

class CreatorsRemoteSource(
    private val api: GamesApi,
) {
    suspend fun getCreatorsRoles(): RolesList =
        api.getCreatorsRoles()

    suspend fun getCreators(): CreatorsList =
        api.getCreators()
}