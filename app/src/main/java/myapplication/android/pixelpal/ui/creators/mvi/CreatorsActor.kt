package myapplication.android.pixelpal.ui.creators.mvi

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.pixelpal.domain.usecase.creators.GetCreatorsUseCase
import myapplication.android.pixelpal.domain.usecase.publishers.GetPublishersUseCase
import myapplication.android.pixelpal.ui.creators.model.creatores.CreatorsUiList
import myapplication.android.pixelpal.ui.creators.model.creatores.toUi
import myapplication.android.pixelpal.ui.creators.model.publisher.toUi
import myapplication.android.pixelpal.ui.ktx.asyncAwait
import myapplication.android.pixelpal.ui.ktx.runCatchingNonCancellation
import myapplication.android.pixelpal.ui.mvi.MviActor

class CreatorsActor(
    private val getCreatorsUseCase: GetCreatorsUseCase,
    private val getPublishersUseCase: GetPublishersUseCase
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
            is CreatorsIntent.GetRolesCreators -> loadCreators(state.page + 1, intent.roleId)
            CreatorsIntent.Init -> init()
            CreatorsIntent.GetPublishers -> loadPublishers(state.page + 1)
        }

    private fun loadPublishers(page: Int): Flow<CreatorsPartialState> =
        flow {
            kotlin.runCatching {
                getPublishers(page)
            }.fold(
                onSuccess = { data ->
                    emit(CreatorsPartialState.DataLoaded(data))
                },
                onFailure = {exception ->
                    CreatorsPartialState.Error(exception)
                }
            )
        }

    private fun init() = flow { emit(CreatorsPartialState.Loading) }

    private fun loadCreators(
        page: Int,
        roleId: Int
    ): Flow<CreatorsPartialState> =
        flow {
            kotlin.runCatching {
                Log.i("Page", page.toString())
               getCreators(page, roleId)
            }.fold(
                onSuccess = { data ->
                    emit(CreatorsPartialState.DataLoaded(data))
                },
                onFailure = {
                    emit(CreatorsPartialState.Error(it))
                }
            )
        }

    private suspend fun getPublishers(page: Int) =
        runCatchingNonCancellation {
            asyncAwait(
                {getPublishersUseCase.invoke(page)}
            ){ publishers ->
                publishers.toUi()
            }
        }.getOrThrow()

    private suspend fun getCreators(
        page: Int,
        roleId: Int
    ): CreatorsUiList =
        runCatchingNonCancellation {
            asyncAwait(
                { getCreatorsUseCase.invoke(page, roleId) }
            ) { creators ->
                creators.toUi()
            }
        }.getOrThrow()

}