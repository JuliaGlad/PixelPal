package myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.pixelpal.domain.usecase.games.GetGameByPublisherUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGamesByPlatformUseCase
import myapplication.android.pixelpal.domain.usecase.platofrms.GetPlatformDetailsUseCase
import myapplication.android.pixelpal.ui.creator_details.mvi.CreatorDetailsPartialState
import myapplication.android.pixelpal.ui.home.model.toUi
import myapplication.android.pixelpal.ui.ktx.asyncAwait
import myapplication.android.pixelpal.ui.ktx.runCatchingNonCancellation
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviActor
import myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.model.PlatformDetailsResult
import myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.model.toUi

class PlatformDetailsActor(
    private val getPlatformDetailsUseCase: GetPlatformDetailsUseCase,
    private val getGameByPlatformUseCase: GetGamesByPlatformUseCase
): MviActor<
        PlatformDetailsPartialState,
        PlatformDetailsIntent,
        PlatformDetailsState,
        PlatformDetailsEffect>() {
    override fun resolve(
        intent: PlatformDetailsIntent,
        state: PlatformDetailsState
    ): Flow<PlatformDetailsPartialState> =
        when(intent){
            is PlatformDetailsIntent.GetPlatformDetails -> loadPlatformDetails(intent.id, state.page + 1)
            PlatformDetailsIntent.Init -> init()
            is PlatformDetailsIntent.GetGames -> loadGames(
                (state.ui as LceState.Content).data,
                intent.id,
                state.page + 1
            )
        }

    private fun loadGames(platformDetailsResult: PlatformDetailsResult, id: Int, page: Int) =
        flow {
            kotlin.runCatching {
                getGames(id, page)
            }.fold(
                onSuccess = { data ->
                    val newItems = platformDetailsResult.newItems
                    if (newItems != null) {
                        platformDetailsResult.newItems!!.games.clear()
                        platformDetailsResult.newItems = null
                    }
                    platformDetailsResult.newItems = data
                    emit(PlatformDetailsPartialState.DataLoaded(platformDetailsResult))
                },
                onFailure = { throwable ->
                    emit(PlatformDetailsPartialState.Error(throwable))
                }
            )
        }

    private fun loadPlatformDetails(id: Int, page: Int) =
        flow {
            kotlin.runCatching {
                PlatformDetailsResult(
                    getPlatformDetails(id),
                    getGames(id, page)
                )
            }.fold(
                onSuccess = { data ->
                    emit(PlatformDetailsPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(PlatformDetailsPartialState.Error(throwable))
                }
            )
        }

    private suspend fun getGames(id: Int, page: Int) =
        runCatchingNonCancellation {
            asyncAwait(
                {getGameByPlatformUseCase.invoke(id, page)}
            ){data ->
                data.toUi()
            }
        }.getOrThrow()

    private suspend fun getPlatformDetails(id: Int) =
        runCatchingNonCancellation {
            asyncAwait(
                {getPlatformDetailsUseCase.invoke(id)}
            ){ data ->
                data.toUi()
            }
        }.getOrThrow()

    private fun init() = flow { emit(PlatformDetailsPartialState.Loading) }

}