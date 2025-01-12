package myapplication.android.pixelpal.data.repository.creators

import myapplication.android.pixelpal.data.models.creators.CreatorDetails
import myapplication.android.pixelpal.domain.model.creator.CreatorDetailsDomain
import myapplication.android.pixelpal.domain.model.creator.CreatorDomainList
import myapplication.android.pixelpal.domain.model.creator.RoleDomain
import myapplication.android.pixelpal.ui.game_details.model.CreatorsGameUiList

interface CreatorsRepository {

    suspend fun getCreatorDetails(id: Long): CreatorDetailsDomain

    suspend fun getGameCreators(gameId: String,  page: Int): CreatorDomainList

    suspend fun getCreatorsRoles(): List<RoleDomain>

    suspend fun getCreators(page: Int, roleId: Int): CreatorDomainList
}