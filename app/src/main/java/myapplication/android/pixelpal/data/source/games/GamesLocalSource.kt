package myapplication.android.pixelpal.data.source.games

import myapplication.android.pixelpal.data.models.gamesNews.GamesNewsList

interface GamesLocalSource {

    fun getTopGames(): GamesNewsList?

    fun getGameReleases(): GamesNewsList?

    fun insertTopGames(games: GamesNewsList)

    fun insertGameReleases(games: GamesNewsList)

    fun deleteTopGames()

    fun deleteGameReleases()
}