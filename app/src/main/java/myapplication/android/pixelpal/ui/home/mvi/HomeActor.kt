package myapplication.android.pixelpal.ui.home.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import myapplication.android.pixelpal.domain.usecase.games.GetGamesReleasesUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetTopGamesUseCase
import myapplication.android.pixelpal.ui.home.mvi.HomeExtensions.combine
import myapplication.android.pixelpal.ui.home.mvi.HomeExtensions.flatten
import myapplication.android.pixelpal.ui.home.mvi.HomeExtensions.toList
import myapplication.android.pixelpal.ui.mvi.MviActor

//TODO("Add date param")
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
                val triple = combine(
                    getTopGamesUseCase.invoke().asFlow(),
                    getGamesReleasesUseCase.invoke("$startDate,$currentDate").asFlow(),
                    getGamesReleasesUseCase.invoke("$currentDate,$endMonthDate").asFlow(),
                ){ top, releases, nextReleases ->
                   Triple(top, releases, nextReleases)
                }
                triple.toList()
            }.fold(
                onSuccess = { data ->
                    val (topGames, gamesReleased, monthReleases) = data.flatten()
                    val topUi = topGames.toList()
                    val releasedUi = gamesReleased.toList()
                    val monthReleasesUI = monthReleases.toList()

                    emit(
                        HomePartialState.DataLoaded(
                            HomeContentResult(
                                topUi,
                                releasedUi,
                                monthReleasesUI
                            )
                        )
                    )
                },
                onFailure = { exception ->
                    emit(HomePartialState.Error(exception))
                }
            )
        }
}