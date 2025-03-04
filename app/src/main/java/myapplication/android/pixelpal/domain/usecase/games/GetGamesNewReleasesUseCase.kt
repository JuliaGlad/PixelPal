package myapplication.android.pixelpal.domain.usecase.games

import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.domain.mapper.games.toDomain
import myapplication.android.pixelpal.domain.model.games.GamesMainInfoListDomain
import javax.inject.Inject

class GetGamesNewReleasesUseCase @Inject constructor(
    private val repository: GamesRepository
) {
    suspend fun invoke(date: String, page: Int): GamesMainInfoListDomain =
        repository.getGameNewReleases(date, page).toDomain()
}