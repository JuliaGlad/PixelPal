package myapplication.android.pixelpal.data.database.provider

import myapplication.android.pixelpal.app.App.Companion.app
import myapplication.android.pixelpal.data.database.entities.GenreEntity
import myapplication.android.pixelpal.data.models.genres.GenresList

class GenreProvider {

    fun getGenres(): List<GenreEntity> =
        app.database.genreDao().getAll()

    fun deleteGenres() { app.database.genreDao().deleteAll() }

    fun insertGenres(games: GenresList) {
        val entities = mutableListOf<GenreEntity>()
        for (i in games.items){
            with(i){
                entities.add(
                    GenreEntity(
                        id,
                        name
                    )
                )
            }
        }
        app.database.genreDao().insertAll(entities)
    }

    fun updateGenres(entity: GenreEntity, description: String) {
        val entities = app.database.genreDao().getAll()
        for (i in entities){
            if (i.genreId == entity.genreId){
                i.description = description
            }
        }
        app.database.genreDao().updateGenre(entity)
    }
}