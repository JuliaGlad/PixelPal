package myapplication.android.pixelpal.data.source.games

import myapplication.android.pixelpal.data.models.gamesMain.GamesShortDataList

interface GamesShortDataLocalSource {

    fun insertGamesShortData(games: GamesShortDataList)

    fun getGamesShortData(): GamesShortDataList?

    fun deleteGamesShortData()
}