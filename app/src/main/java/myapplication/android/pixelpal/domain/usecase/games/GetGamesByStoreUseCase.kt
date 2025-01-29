package myapplication.android.pixelpal.domain.usecase.games

import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.domain.mapper.games.toDomain
import myapplication.android.pixelpal.domain.model.games.GamesNewsListDomain
import javax.inject.Inject

class GetGamesByStoreUseCase @Inject constructor(
    private val gamesRepository: GamesRepository
) {
    suspend fun invoke(storeId: Int, page: Int): GamesNewsListDomain =
        gamesRepository.getGameByStore(storeId, page).toDomain()
}