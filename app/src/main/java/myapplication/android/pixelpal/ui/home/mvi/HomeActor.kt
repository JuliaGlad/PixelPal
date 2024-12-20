package myapplication.android.pixelpal.ui.home.mvi

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.pixelpal.domain.usecase.games.GetGamesReleasesUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetTopGamesUseCase
import myapplication.android.pixelpal.ui.home.model.toUi
import myapplication.android.pixelpal.ui.ktx.asyncAwait
import myapplication.android.pixelpal.ui.ktx.runCatchingNonCancellation
import myapplication.android.pixelpal.ui.mvi.MviActor

class HomeActor(
    private val getTopGamesUseCase: GetTopGamesUseCase,
    private val getGamesReleasesUseCase: GetGamesReleasesUseCase
) : MviActor<
        HomePartialState,
        HomeIntent,
        HomeState,
        HomeEffect>() {

    override fun resolve(
        intent: HomeIntent,
        state: HomeState
    ): Flow<HomePartialState> =
        when (intent) {
            HomeIntent.Init -> init()
            is HomeIntent.GetGames -> loadGames(
                intent.currentDate,
                intent.monthEndDate,
                intent.monthStartDate
            )
        }

    private fun init() =
        flow {
            emit(HomePartialState.Loading)
        }

    private fun loadGames(
        currentDate: String,
        endMonthDate: String,
        startDate: String
    ): Flow<HomePartialState> =
        flow {
            kotlin.runCatching {
                getGames(currentDate, endMonthDate, startDate)
            }.fold(
                onSuccess = { data ->
                    emit(
                        HomePartialState.DataLoaded(data)
                    )
                },
                onFailure = { exception ->
                    emit(HomePartialState.Error(exception))
                }
            )
        }

    private suspend fun getGames(
        currentDate: String,
        endMonthDate: String,
        startDate: String
    ): HomeContentResult =
        runCatchingNonCancellation {
            asyncAwait(
                { getTopGamesUseCase.invoke() },
                { getGamesReleasesUseCase.invoke("$startDate,$currentDate") },
                { getGamesReleasesUseCase.invoke("$currentDate,$endMonthDate") },
            ) { top, released, nextReleases ->
                HomeContentResult(
                    top.toUi(),
                    released.toUi(),
                    nextReleases.toUi()
                )
            }
        }.getOrThrow()

}