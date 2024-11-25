package myapplication.android.pixelpal.ui.games.main.mvi

import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviStore

class MainGamesStore(
    actor: MainGamesActor,
    reducer: MainGamesReducer
): MviStore<
        MainGamesPartialState,
        MainGamesIntent,
        MainGamesState,
        MainGamesEffects>(reducer, actor) {

    override fun initialStateCreator(): MainGamesState = MainGamesState(LceState.Loading)
}