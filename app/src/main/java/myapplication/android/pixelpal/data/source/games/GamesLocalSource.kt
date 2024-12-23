package myapplication.android.pixelpal.data.source.games

import myapplication.android.pixelpal.data.models.gamesNews.GamesNewsList

interface GamesLocalSource {

    fun getTopGames(currentPage: Int): GamesNewsList?

    fun getGameMonthReleases(dates: String, page: Int): GamesNewsList?

    fun getGameNewReleases(dates: String, page: Int): GamesNewsList?

    fun insertTopGames(games: GamesNewsList, currentPage: Int)

    fun insertGameReleases(games: GamesNewsList, currentPage: Int)

    fun deleteTopGames()

    fun deleteGameReleases()
}