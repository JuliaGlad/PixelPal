package myapplication.android.pixelpal.ui.game_details.mvi

import myapplication.android.pixelpal.ui.mvi.MviStore

class GameDetailsStore(
    gameDetailsReducer: GameDetailsReducer,
    gameDetailsActor: GameDetailsActor
): MviStore<GameDetailsPartialState, GameDetailsIntent, GameDetailsState, GameDetailsEffect>(
    gameDetailsReducer,
    gameDetailsActor
) {
    override fun initialStateCreator(): GameDetailsState = GameDetailsState(GameDetailsLceState.Loading)
}