package myapplication.android.pixelpal.data.source.games

import myapplication.android.pixelpal.data.models.gamesMain.GamesShortDataList

interface GamesShortDataLocalSource {

    fun insertGamesShortData(currentPage: Int, games: GamesShortDataList)

    fun getGamesShortData(page: Int): GamesShortDataList?

    fun deleteGamesShortData()
}