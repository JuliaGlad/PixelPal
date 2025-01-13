package myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.pixelpal.domain.usecase.platofrms.GetPlatformDetailsUseCase
import myapplication.android.pixelpal.ui.ktx.asyncAwait
import myapplication.android.pixelpal.ui.ktx.runCatchingNonCancellation
import myapplication.android.pixelpal.ui.mvi.MviActor
import myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.model.toUi

class PlatformDetailsActor(
    private val getPlatformDetailsUseCase: GetPlatformDetailsUseCase
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
            is PlatformDetailsIntent.GetPlatformDetails -> loadPlatformDetails(intent.id)
            PlatformDetailsIntent.Init -> init()
        }

    private fun loadPlatformDetails(id: Int) =
        flow {
            kotlin.runCatching {
                getPlatformDetails(id)
            }.fold(
                onSuccess = { data ->
                    emit(PlatformDetailsPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(PlatformDetailsPartialState.Error(throwable))
                }
            )
        }

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