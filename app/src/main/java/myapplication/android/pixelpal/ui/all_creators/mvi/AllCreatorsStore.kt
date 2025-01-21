package myapplication.android.pixelpal.ui.all_creators.mvi

import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviState
import myapplication.android.pixelpal.ui.mvi.MviStore

class AllCreatorsStore(
    reducer: AllCreatorsReducer,
    actor: AllCreatorsActor
) : MviStore<
        AllCreatorsPartialState,
        AllCreatorsIntent,
        AllCreatorsState,
        AllCreatorsEffect>(
    reducer, actor
) {
    override fun initialStateCreator(): AllCreatorsState = AllCreatorsState(LceState.Loading)
}