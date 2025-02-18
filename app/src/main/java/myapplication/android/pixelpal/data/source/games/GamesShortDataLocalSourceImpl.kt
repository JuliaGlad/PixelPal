package myapplication.android.pixelpal.data.source.games

import myapplication.android.pixelpal.data.database.entities.GamesShortEntity
import myapplication.android.pixelpal.data.database.provider.GamesShortProvider
import myapplication.android.pixelpal.data.models.gamesMain.GameShortData
import myapplication.android.pixelpal.data.models.gamesMain.GamesShortDataList
import java.util.stream.Collectors
import javax.inject.Inject

class GamesShortDataLocalSourceImpl @Inject constructor() : GamesShortDataLocalSource {

    private fun GamesShortEntity.toGameShortData() =
        GameShortData(releaseDate, image, ageRating, rating, playTime, gameId, title)

    override fun insertGamesShortData(currentPage: Int, games: GamesShortDataList, genres: Long) {
        GamesShortProvider().insertGamesShort(currentPage, games, genres)
    }

    override fun getGamesShortData(page: Int, genres: Long): GamesShortDataList? {
        val data = GamesShortProvider().getGamesShort(page, genres)
        return if (data.isNotEmpty()) {
            GamesShortDataList(
                data.stream()
                    .map { it.toGameShortData() }
                    .collect(Collectors.toList())
            )
        } else null
    }

    override fun deleteGamesShortData() {
        GamesShortProvider().deleteGamesShort()
    }

}