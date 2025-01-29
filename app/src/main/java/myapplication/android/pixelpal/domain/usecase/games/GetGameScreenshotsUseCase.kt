package myapplication.android.pixelpal.domain.usecase.games

import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.domain.mapper.screenshots.toDomain
import myapplication.android.pixelpal.domain.model.screenshot.ScreenShotDomainList
import javax.inject.Inject

class GetGameScreenshotsUseCase @Inject constructor(
    val gamesRepository: GamesRepository
) {
    suspend fun invoke(gameId: String): ScreenShotDomainList =
        gamesRepository.getGameScreenshots(gameId).toDomain()
}