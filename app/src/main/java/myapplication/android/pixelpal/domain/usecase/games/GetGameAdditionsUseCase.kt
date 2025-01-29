package myapplication.android.pixelpal.domain.usecase.games

import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.domain.mapper.games.toDomain
import myapplication.android.pixelpal.domain.model.games.GamesShortDomainList
import javax.inject.Inject

class GetGameAdditionsUseCase @Inject constructor(
    val repository: GamesRepository
) {
    suspend fun invoke(gameId: String, page: Int): GamesShortDomainList
    = repository.getGameAddition(gameId, page).toDomain()
}