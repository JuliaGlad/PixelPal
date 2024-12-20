package myapplication.android.pixelpal.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.json.JsonArray
import myapplication.android.pixelpal.data.models.GameRating
import myapplication.android.pixelpal.data.models.creators_roles.Role

abstract class Converter<T>(
    private val typeToken: TypeToken<T>
) {
    private val gson = Gson()
    @TypeConverter
    fun toJson(value: T): String {
        return gson.toJson(value, typeToken.type)
    }

    @TypeConverter
    fun fromJson(json: String): T {
        return gson.fromJson(json, typeToken)
    }
}

abstract class ListConverter<T>(
    private val typeToken: TypeToken<List<T>>
) {
    private val gson = Gson()
    @TypeConverter
    fun toJson(value: List<T>): String {
        return gson.toJson(value, typeToken.type)
    }

    @TypeConverter
    fun fromJson(json: String): List<T> {
        return gson.fromJson(json, typeToken)
    }
}

class JsonArrayConverter : Converter<JsonArray>(TypeToken.get(JsonArray::class.java))

class RoleListConverter : ListConverter<Role>(object : TypeToken<List<Role>>(){})

class GameRatingConverter : Converter<GameRating?>(TypeToken.get(GameRating::class.java))