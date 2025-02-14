package myapplication.android.pixelpal.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.serialization.json.JsonArray
import myapplication.android.pixelpal.data.database.converters.GameRatingConverter
import myapplication.android.pixelpal.data.database.converters.JsonArrayConverter
import myapplication.android.pixelpal.data.database.converters.RoleListConverter
import myapplication.android.pixelpal.data.database.dao.CreatorRolesDao
import myapplication.android.pixelpal.data.database.dao.CreatorsDao
import myapplication.android.pixelpal.data.database.dao.FavoriteGamesDao
import myapplication.android.pixelpal.data.database.dao.GameTopDao
import myapplication.android.pixelpal.data.database.dao.GameShortDao
import myapplication.android.pixelpal.data.database.dao.GamesReleaseDao
import myapplication.android.pixelpal.data.database.dao.GenreDao
import myapplication.android.pixelpal.data.database.dao.PlatformDao
import myapplication.android.pixelpal.data.database.dao.PublisherDao
import myapplication.android.pixelpal.data.database.dao.StoreDao
import myapplication.android.pixelpal.data.database.entities.CreatorEntity
import myapplication.android.pixelpal.data.database.entities.CreatorsRolesEntity
import myapplication.android.pixelpal.data.database.entities.FavoriteGamesEntity
import myapplication.android.pixelpal.data.database.entities.GameReleaseEntity
import myapplication.android.pixelpal.data.database.entities.GameTopEntity
import myapplication.android.pixelpal.data.database.entities.GamesShortEntity
import myapplication.android.pixelpal.data.database.entities.GenreEntity
import myapplication.android.pixelpal.data.database.entities.PlatformEntity
import myapplication.android.pixelpal.data.database.entities.PublisherEntity
import myapplication.android.pixelpal.data.database.entities.StoreEntity

@TypeConverters(
    value = [
        GameRatingConverter::class,
        RoleListConverter::class,
        JsonArrayConverter::class
    ]
)
@Database(
    entities = [
        FavoriteGamesEntity::class,
        GameReleaseEntity::class,
        GameTopEntity::class,
        CreatorEntity::class,
        CreatorsRolesEntity::class,
        GamesShortEntity::class,
        GenreEntity::class,
        PlatformEntity::class,
        PublisherEntity::class,
        StoreEntity::class
    ],
    version = 1
)
abstract class LocalDataBase : RoomDatabase() {

    abstract fun favoriteGamesDao(): FavoriteGamesDao

    abstract fun gameTopDao(): GameTopDao

    abstract fun gameReleasesDao(): GamesReleaseDao

    abstract fun creatorRolesDao(): CreatorRolesDao

    abstract fun creatorsDao(): CreatorsDao

    abstract fun gamesShortDao(): GameShortDao

    abstract fun genreDao(): GenreDao

    abstract fun platformDao(): PlatformDao

    abstract fun publisherDao(): PublisherDao

    abstract fun storeDao(): StoreDao

}