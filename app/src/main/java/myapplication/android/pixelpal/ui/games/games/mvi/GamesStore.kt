package myapplication.android.pixelpal.ui.games.games.mvi

import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviStore

class GamesStore(
    reducer: GamesReducer,
    actor: GamesActor
): MviStore<
        GamesPartialState,
        GamesIntent,
        GamesState,
        GamesEffects>(reducer, actor) {
    override fun initialStateCreator(): GamesState = GamesState(LceState.Loading)
}