package myapplication.android.pixelpal.data.source.creators

import myapplication.android.pixelpal.data.api.GamesApi
import myapplication.android.pixelpal.data.models.creators.CreatorDetails
import myapplication.android.pixelpal.data.models.creators.CreatorsList
import myapplication.android.pixelpal.data.models.creators_roles.RolesList
import javax.inject.Inject

class CreatorsRemoteSourceImpl @Inject constructor(
    private val api: GamesApi,
): CreatorsRemoteSource {

    override suspend fun getCreatorDetails(id: Long): CreatorDetails =
        api.getCreatorDetails(id)

    override suspend fun getGameCreators(gameId: String, page: Int): CreatorsList =
        api.getGameCreators(gameId, page)

    override suspend fun getCreatorsRoles(): RolesList =
        api.getCreatorsRoles()

    override suspend fun getCreators(page: Int): CreatorsList =
        api.getCreators(page)
}