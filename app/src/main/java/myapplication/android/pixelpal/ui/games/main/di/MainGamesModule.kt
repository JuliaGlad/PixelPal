package myapplication.android.pixelpal.ui.games.main.di

import dagger.Binds
import dagger.Module
import myapplication.android.pixelpal.data.repository.genres.GenresRepository
import myapplication.android.pixelpal.data.repository.genres.GenresRepositoryImpl
import myapplication.android.pixelpal.data.source.genres.GenresLocalSource
import myapplication.android.pixelpal.data.source.genres.GenresLocalSourceImpl
import myapplication.android.pixelpal.data.source.genres.GenresRemoteSource
import myapplication.android.pixelpal.data.source.genres.GenresRemoteSourceImpl

@Module
interface MainGamesModule {

    @MainGamesScope
    @Binds
    fun bindGenresRepository(genresRepositoryImpl: GenresRepositoryImpl): GenresRepository

    @MainGamesScope
    @Binds
    fun bindGenresRemoteSource(genresRemoteSourceImpl: GenresRemoteSourceImpl): GenresRemoteSource

    @MainGamesScope
    @Binds
    fun bindGenresLocalSource(genreLocalSourceImpl: GenresLocalSourceImpl): GenresLocalSource

}