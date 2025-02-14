package myapplication.android.pixelpal.data.source.favorite_games

import myapplication.android.pixelpal.data.api.GamesApi
import myapplication.android.pixelpal.data.models.gamesMain.GamesShortDataList
import myapplication.android.pixelpal.data.models.gamesNews.GamesMainInfoList
import javax.inject.Inject

interface FavoriteGamesRemoteSource {

    suspend fun getGamesById(ids: List<Long>): GamesMainInfoList

}