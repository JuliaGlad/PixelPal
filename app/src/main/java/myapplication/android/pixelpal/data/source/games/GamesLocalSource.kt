package myapplication.android.pixelpal.data.source.games

import myapplication.android.pixelpal.data.models.gamesNews.GamesMainInfoList

interface GamesLocalSource {

    fun getTopGames(currentPage: Int): GamesMainInfoList?

    fun getGameMonthReleases(dates: String, page: Int): GamesMainInfoList?

    fun getGameNewReleases(dates: String, page: Int): GamesMainInfoList?

    fun insertTopGames(games: GamesMainInfoList, currentPage: Int)

    fun insertGameReleases(games: GamesMainInfoList, currentPage: Int)

    fun deleteTopGames()

    fun deleteGameReleases()
}