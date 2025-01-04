package myapplication.android.pixelpal.domain.usecase.games

import myapplication.android.pixelpal.data.repository.games.GamesRepository
import javax.inject.Inject

class GetGameAdditionsUseCase @Inject constructor(
    val repository: GamesRepository
) {
    suspend fun invoke(gameId: String, page: Int) = repository.getGameAddition(gameId, page)
}