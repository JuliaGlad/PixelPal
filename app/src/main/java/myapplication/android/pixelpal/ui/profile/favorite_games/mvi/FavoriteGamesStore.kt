package myapplication.android.pixelpal.ui.profile.favorite_games.mvi

import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviStore

class FavoriteGamesStore(
    actor: FavoriteGamesActor,
    reducer: FavoriteGamesReducer
): MviStore<
        FavoriteGamesPartialState,
        FavoriteGamesIntent,
        FavoriteGamesState,
        FavoriteGamesEffect>(reducer, actor) {
    override fun initialStateCreator(): FavoriteGamesState = FavoriteGamesState(ui = LceState.Loading)
}