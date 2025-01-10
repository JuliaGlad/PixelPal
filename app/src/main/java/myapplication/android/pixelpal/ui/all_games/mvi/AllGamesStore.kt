package myapplication.android.pixelpal.ui.all_games.mvi

import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviStore

class AllGamesStore(
    val reducer: AllGamesReducer,
    val actor: AllGamesActor
) : MviStore<
        AllGamesPartialState,
        AllGamesIntent,
        AllGamesState,
        AllGamesEffect>(
    reducer, actor
) {
    override fun initialStateCreator(): AllGamesState = AllGamesState(LceState.Loading)
}