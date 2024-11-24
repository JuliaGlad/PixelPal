package myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.pixelpal.domain.usecase.platofrms.GetPlatformsUseCase
import myapplication.android.pixelpal.ui.ktx.asyncAwait
import myapplication.android.pixelpal.ui.ktx.runCatchingNonCancellation
import myapplication.android.pixelpal.ui.mvi.MviActor
import myapplication.android.pixelpal.ui.platforms.fragments.platform.model.PlatformUiList
import myapplication.android.pixelpal.ui.platforms.fragments.platform.model.toUi

class PlatformActor(
    private val getPlatformsUseCase: GetPlatformsUseCase
) : MviActor<
        PlatformPartialState,
        PlatformIntent,
        PlatformState,
        PlatformEffect>() {

    override fun resolve(intent: PlatformIntent, state: PlatformState): Flow<PlatformPartialState> =
        when (intent) {
            is PlatformIntent.GetPlatforms -> loadPlatforms()
            PlatformIntent.Init -> init()
        }

    private fun init() = flow { emit(PlatformPartialState.Loading) }

    private fun loadPlatforms() =
        flow {
            kotlin.runCatching {
                getPlatforms()
            }.fold(
                onSuccess = { data ->
                    emit(PlatformPartialState.DataLoaded(data))
                },
                onFailure = {exception ->
                    emit(PlatformPartialState.Error(exception))
                }
            )
        }

    private suspend fun getPlatforms(): PlatformUiList =
        runCatchingNonCancellation {
             asyncAwait(
                {getPlatformsUseCase.invoke()}
            ){ platforms ->
                 platforms.toUi()
             }
        }.getOrThrow()
}