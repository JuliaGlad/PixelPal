package myapplication.android.pixelpal.ui.creators.mvi

import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviStore

class CreatorsStore(
    reducer: CreatorsReducer,
    actor: CreatorsActor
) : MviStore<CreatorsPartialState, CreatorsIntent, CreatorsState, CreatorsEffect>(
    reducer, actor
){
    override fun initialStateCreator(): CreatorsState = CreatorsState(ui = LceState.Loading)
}