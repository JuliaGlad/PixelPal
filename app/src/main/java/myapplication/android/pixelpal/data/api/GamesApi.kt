package myapplication.android.pixelpal.data.api

import myapplication.android.pixelpal.data.models.creators.CreatorsList
import myapplication.android.pixelpal.data.models.creators_roles.RolesList
import myapplication.android.pixelpal.data.models.gamesMain.GamesShortDataList
import myapplication.android.pixelpal.data.models.gamesNews.GamesNewsList
import myapplication.android.pixelpal.data.models.genres.GenreDescription
import myapplication.android.pixelpal.data.models.genres.GenresList
import myapplication.android.pixelpal.data.models.platforms.PlatformsList
import myapplication.android.pixelpal.data.models.publishers.PublishersList
import myapplication.android.pixelpal.data.models.stores.StoresList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GamesApi {

    @GET("games")
    suspend fun getGamesByReleasesDate(@Query("dates") dates: String, @Query("page") page: Int): GamesNewsList

    @GET("games?ordering=-metacritics")
    suspend fun getTopGames(@Query("page") page: Int): GamesNewsList

    @GET("games")
    suspend fun getGamesShortDataByGenre(@Query("page")page: Int, @Query("genres") genre: Long): GamesShortDataList

    @GET("creator-roles")
    suspend fun getCreatorsRoles(): RolesList

    @GET("creators")
    suspend fun getCreators(@Query("page")page: Int): CreatorsList

    @GET("publishers")
    suspend fun getPublishers(@Query("page")page: Int): PublishersList

    @GET("stores")
    suspend fun getStores(@Query("page")page: Int): StoresList

    @GET("platforms")
    suspend fun getPlatforms(@Query("page")page: Int): PlatformsList

    @GET("genres")
    suspend fun getGenresData(): GenresList

    @GET("genres/{id}")
    suspend fun getGenreDescription(
        @Path("id") id: Long
    ): GenreDescription
}