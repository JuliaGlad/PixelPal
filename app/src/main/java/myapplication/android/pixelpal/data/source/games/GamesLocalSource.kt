package myapplication.android.pixelpal.data.source.games

import myapplication.android.pixelpal.data.models.gamesNews.GamesNewsList

interface GamesLocalSource {

    fun getTopGames(): GamesNewsList?

    fun getGameMonthReleases(dates: String): GamesNewsList?

    fun getGameNewReleases(dates: String): GamesNewsList?

    fun insertTopGames(games: GamesNewsList)

    fun insertGameReleases(games: GamesNewsList)

    fun deleteTopGames()

    fun deleteGameReleases()
}