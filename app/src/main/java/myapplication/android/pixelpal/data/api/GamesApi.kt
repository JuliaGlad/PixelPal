package myapplication.android.pixelpal.data.api

import myapplication.android.pixelpal.data.models.creators.CreatorDetails
import myapplication.android.pixelpal.data.models.creators.CreatorsList
import myapplication.android.pixelpal.data.models.creators_roles.RolesList
import myapplication.android.pixelpal.data.models.game_description.GameDescription
import myapplication.android.pixelpal.data.models.gamesMain.GamesShortDataList
import myapplication.android.pixelpal.data.models.gamesNews.GamesNewsList
import myapplication.android.pixelpal.data.models.genres.GenreDescription
import myapplication.android.pixelpal.data.models.genres.GenresList
import myapplication.android.pixelpal.data.models.platforms.PlatformDetails
import myapplication.android.pixelpal.data.models.platforms.PlatformsList
import myapplication.android.pixelpal.data.models.publishers.PublisherDetails
import myapplication.android.pixelpal.data.models.publishers.PublishersList
import myapplication.android.pixelpal.data.models.screenshots.ScreenshotsList
import myapplication.android.pixelpal.data.models.stores.StoreDetails
import myapplication.android.pixelpal.data.models.stores.store.StoresList
import myapplication.android.pixelpal.data.models.stores.store_link.StoreLinksList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GamesApi {

    @GET("games")
    suspend fun getGamesByCreator(@Query("creators")creatorId: Long): GamesNewsList

    @GET("publishers/{id}")
    suspend fun getPublisherDetails(@Path("id")id: Long): PublisherDetails

    @GET("stores/{id}")
    suspend fun getStoreDetails(@Path("id")id: Int): StoreDetails

    @GET("platforms/{id}")
    suspend fun getPlatformDetails(@Path("id")id: Int): PlatformDetails

    @GET("creators/{id}")
    suspend fun getCreatorDetails(@Path("id")id: Long): CreatorDetails

    @GET("games/{id}")
    suspend fun getGameDescription(@Path("id")id: Long): GameDescription

    @GET("games/{game_pk}/stores")
    suspend fun getStoresSellingGame(@Path("game_pk")id: String, @Query("page")page: Int): StoreLinksList

    @GET("games/{game_pk}/parent-games")
    suspend fun getParentGames(@Path("game_pk")id: String, @Query("page") page: Int): GamesShortDataList

    @GET("games/{game_pk}/screenshots")
    suspend fun getGameScreenshots(@Path("game_pk")id: String): ScreenshotsList

    @GET("games/{game_pk}/game-series")
    suspend fun getGamesFromSameSeries(@Path("game_pk") id: String, @Query("page") page: Int): GamesShortDataList

    @GET("games/{game_pk}/development-team")
    suspend fun getGameCreators(@Path("game_pk")id: String, @Query("page") page: Int): CreatorsList

    @GET("games/{game_pk}/additions")
    suspend fun getGameAdditions(@Path("game_pk") id: String, @Query("page") page: Int): GamesShortDataList

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