package myapplication.android.pixelpal.ui.games.main.mvi

import myapplication.android.pixelpal.data.repository.genres.GenresRepository
import myapplication.android.pixelpal.domain.usecase.genres.GetGenresUseCase
import javax.inject.Inject

class MainGamesLocalDI @Inject constructor(
    private val genresRepository: GenresRepository
) {

    private val getGenresUseCase by lazy { GetGenresUseCase(genresRepository) }

    val actor by lazy { MainGamesActor(getGenresUseCase) }

    val reducer by lazy { MainGamesReducer() }
}