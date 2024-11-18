package myapplication.android.pixelpal.ui.home.mvi

import myapplication.android.pixelpal.ui.mvi.MviStore

class HomeStore(
    reducer: HomeReducer,
    actor: HomeActor
) : MviStore<HomePartialState, HomeIntent, HomeState, HomeEffect>(
    reducer, actor
){
    override fun initialStateCreator(): HomeState = HomeState(ui = LceState.Loading)
}