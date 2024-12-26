package myapplication.android.pixelpal.data.source.games

import myapplication.android.pixelpal.data.models.gamesMain.GamesShortDataList

interface GamesShortDataLocalSource {

    fun insertGamesShortData(currentPage: Int, games: GamesShortDataList, genres: Long)

    fun getGamesShortData(page: Int, genres: Long): GamesShortDataList?

    fun deleteGamesShortData()
}