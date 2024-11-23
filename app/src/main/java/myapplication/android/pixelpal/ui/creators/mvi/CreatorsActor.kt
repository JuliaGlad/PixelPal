package myapplication.android.pixelpal.ui.creators.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.pixelpal.domain.usecase.creators.GetCreatorsUseCase
import myapplication.android.pixelpal.ui.creators.model.creatores.toUi
import myapplication.android.pixelpal.ui.mvi.MviActor
import java.util.stream.Collectors

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
    ): Flow<CreatorsPartialState> =
        when (intent) {
            is CreatorsIntent.GetRolesCreators -> loadCreators(intent.roleId)
            CreatorsIntent.Init -> init()
        }

    private fun init() = flow { emit(CreatorsPartialState.Loading) }

    private fun loadCreators(
        roleId: Int
    ): Flow<CreatorsPartialState> =
        flow {
            kotlin.runCatching {
                getCreatorsUseCase.invoke(roleId)
            }.fold(
                onSuccess = { data ->
                    val ui = data.stream()
                        .map { it.toUi() }
                        .collect(Collectors.toList())
                    emit(CreatorsPartialState.DataLoaded(ui))
                },
                onFailure = {
                    emit(CreatorsPartialState.Error(it))
                }
            )
        }
}