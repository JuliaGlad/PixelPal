package myapplication.android.pixelpal.ui.creators.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.pixelpal.domain.usecase.creators.GetCreatorsUseCase
import myapplication.android.pixelpal.ui.mvi.MviActor

class CreatorsActor(
    private val getCreatorsUseCase: GetCreatorsUseCase
) : MviActor<
        CreatorsPartialState,
        CreatorsIntent,
        CreatorsState,
        CreatorsEffect>() {

    override fun resolve(
        intent: CreatorsIntent,
        state: CreatorsState
    )
            : Flow<CreatorsPartialState> =
        when (intent) {
            is CreatorsIntent.GetRolesPresenters -> loadPresenters(100)
            CreatorsIntent.Init -> init()
        }

    private fun init() = flow { emit(CreatorsPartialState.Loading) }

    private fun loadPresenters(
         roleId: Long
    ): Flow<CreatorsPartialState> =
        flow {
            kotlin.runCatching {
                getCreatorsUseCase.invoke(roleId)
            }
        }
}