package myapplication.android.pixelpal.data.api

import myapplication.android.pixelpal.data.models.creators.CreatorsList
import myapplication.android.pixelpal.data.models.creators_roles.CreatorsRolesList
import myapplication.android.pixelpal.data.models.gamesMain.GamesShortDataList
import myapplication.android.pixelpal.data.models.gamesNews.GamesNewsList
import myapplication.android.pixelpal.data.models.genres.description.GenreDescription
import myapplication.android.pixelpal.data.models.genres.genres.GenresList
import myapplication.android.pixelpal.data.models.platforms.PlatformsList
import myapplication.android.pixelpal.data.models.publishers.PublishersList
import myapplication.android.pixelpal.data.models.stores.StoresList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GamesApi {

    @GET("games")
    suspend fun getGamesByReleasesDate(@Query("date") date: String): GamesNewsList

    @GET("games?ordering=rating")
    suspend fun getTopGames(): GamesNewsList

    @GET("games")
    suspend fun getGamesShortData(): GamesShortDataList

    @GET("creator-roles")
    suspend fun getCreatorsRoles(): CreatorsRolesList

    @GET("creators")
    suspend fun getCreators(): CreatorsList

    @GET("publishers")
    suspend fun getPublishers(): PublishersList

    @GET("stores")
    suspend fun getStores(): StoresList

    @GET("platforms")
    suspend fun getPlatforms(): PlatformsList

    @GET("genres")
    suspend fun getGenresData(): GenresList

    @GET("genres/{id}")
    suspend fun getGenreDescription(
        @Path("id") id: Long
    ): GenreDescription
}