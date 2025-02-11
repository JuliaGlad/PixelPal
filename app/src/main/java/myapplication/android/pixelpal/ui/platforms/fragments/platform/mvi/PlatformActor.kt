package myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.pixelpal.domain.usecase.platofrms.GetPlatformsByQueryUseCase
import myapplication.android.pixelpal.domain.usecase.platofrms.GetPlatformsUseCase
import myapplication.android.pixelpal.ui.ktx.asyncAwait
import myapplication.android.pixelpal.ui.ktx.runCatchingNonCancellation
import myapplication.android.pixelpal.ui.mvi.MviActor
import myapplication.android.pixelpal.ui.platforms.fragments.platform.model.PlatformUiList
import myapplication.android.pixelpal.ui.platforms.fragments.platform.model.toUi

class PlatformActor(
    private val getPlatformsUseCase: GetPlatformsUseCase,
    private val getPlatformsByQueryUseCase: GetPlatformsByQueryUseCase
) : MviActor<
        PlatformPartialState,
        PlatformIntent,
        PlatformState,
        PlatformEffect>() {

    override fun resolve(intent: PlatformIntent, state: PlatformState): Flow<PlatformPartialState> =
        when (intent) {
            is PlatformIntent.GetPlatforms -> loadPlatforms(state.page + 1)
            PlatformIntent.Init -> init()
            is PlatformIntent.GetPlatformsByQuery -> loadPlatformsByQuery(state.page + 1, intent.query)
        }

    private fun init() = flow { emit(PlatformPartialState.Init) }

    private fun loadPlatformsByQuery(page: Int, query: String) =
        flow {
            kotlin.runCatching {
                getPlatformsByQuery(page, query)
            }.fold(
                onSuccess = { data ->
                    emit(PlatformPartialState.DataLoaded(data))
                },
                onFailure = {throwable ->
                    emit(PlatformPartialState.Error(throwable))
                }
            )
        }

    private fun loadPlatforms(page: Int) =
        flow {
            kotlin.runCatching {
                getPlatforms(page)
            }.fold(
                onSuccess = { data ->
                    emit(PlatformPartialState.DataLoaded(data))
                },
                onFailure = { exception ->
                    emit(PlatformPartialState.Error(exception))
                }
            )
        }

    private suspend fun getPlatformsByQuery(page: Int, query: String): PlatformUiList =
        runCatchingNonCancellation {
            asyncAwait(
                { getPlatformsByQueryUseCase.invoke(page, query) }
            ) { platform ->
                platform.toUi()
            }
        }.getOrThrow()

    private suspend fun getPlatforms(page: Int): PlatformUiList =
        runCatchingNonCancellation {
            asyncAwait(
                { getPlatformsUseCase.invoke(page) }
            ) { platforms ->
                platforms.toUi()
            }
        }.getOrThrow()
}