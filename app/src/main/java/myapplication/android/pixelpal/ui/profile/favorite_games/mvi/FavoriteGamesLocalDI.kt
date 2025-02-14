package myapplication.android.pixelpal.ui.profile.favorite_games.mvi

import myapplication.android.pixelpal.data.repository.favorite_games.FavoriteGamesRepository
import myapplication.android.pixelpal.domain.usecase.favorite_games.GetFavoriteGamesUseCase
import myapplication.android.pixelpal.domain.usecase.favorite_games.RemoveFavoriteGameUseCase
import javax.inject.Inject

class FavoriteGamesLocalDI @Inject constructor(
    private val favoriteGamesRepository: FavoriteGamesRepository
){
    private val getFavoriteGamesUseCase by lazy { GetFavoriteGamesUseCase(favoriteGamesRepository) }

    val actor by lazy { FavoriteGamesActor(getFavoriteGamesUseCase) }

    val reducer by lazy { FavoriteGamesReducer() }
}