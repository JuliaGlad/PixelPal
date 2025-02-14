package myapplication.android.pixelpal.ui.home.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.pixelpal.domain.usecase.games.GetGameMonthReleasesUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGamesNewReleasesUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetTopGamesUseCase
import myapplication.android.pixelpal.ui.home.model.GamesMainInfoListUi
import myapplication.android.pixelpal.ui.home.model.toUi
import myapplication.android.pixelpal.ui.ktx.asyncAwait
import myapplication.android.pixelpal.ui.ktx.runCatchingNonCancellation
import myapplication.android.pixelpal.ui.mvi.MviActor

class HomeActor(
    private val getTopGamesUseCase: GetTopGamesUseCase,
    private val getGamesNewReleasesUseCase: GetGamesNewReleasesUseCase,
    private val getGameMonthReleasesUseCase: GetGameMonthReleasesUseCase
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

            is HomeIntent.GetTop -> {
                loadTopGames(state.topPage + 1)
            }

            is HomeIntent.GetNextReleases -> loadNextReleases(
                intent.monthEndDate,
                intent.currentDate,
                state.nextReleasesPage + 1
            )

            is HomeIntent.GetReleases -> loadReleases(
                intent.startDate,
                intent.currentDate,
                state.newReleasesPage + 1
            )
        }

    private fun init() =
        flow {
            emit(HomePartialState.Loading)
        }

    private fun loadTopGames(page: Int): Flow<HomePartialState> =
        flow {
            kotlin.runCatching {
                getTopGames(page)
            }.fold(
                onSuccess = { data ->
                    emit(HomePartialState.TopReleasesUpdated(data))
                },
                onFailure = { throwable ->
                    emit(HomePartialState.Error(throwable))
                }
            )
        }

    private fun loadReleases(
        startDate: String,
        currentDate: String,
        page: Int
    ): Flow<HomePartialState> =
        flow {
            kotlin.runCatching {
                getReleasesGames("$startDate,$currentDate", page)
            }.fold(
                onSuccess = { data ->
                    emit(
                        HomePartialState.ReleasesUpdated(data)
                    )
                },
                onFailure = { throwable ->
                    emit(HomePartialState.Error(throwable))
                }
            )
        }

    private fun loadNextReleases(
        endMonthDate: String,
        currentDate: String,
        page: Int
    ): Flow<HomePartialState> =
        flow {
            kotlin.runCatching {
                getNextReleasesGames("$currentDate,$endMonthDate", page)
            }.fold(
                onSuccess = { data ->
                    emit(
                        HomePartialState.NextReleasesUpdated(data)
                    )
                },
                onFailure = { throwable ->
                    emit(HomePartialState.Error(throwable))
                }
            )
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

    private suspend fun getReleasesGames(
        date: String,
        page: Int
    ): GamesMainInfoListUi =
        runCatchingNonCancellation {
            asyncAwait(
                { getGamesNewReleasesUseCase.invoke(date, page) }
            ) { result ->
                result.toUi()
            }
        }.getOrThrow()

    private suspend fun getNextReleasesGames(
        date: String,
        page: Int
    ): GamesMainInfoListUi =
        runCatchingNonCancellation {
            asyncAwait(
                { getGameMonthReleasesUseCase.invoke(date, page) }
            ) { result ->
                result.toUi()
            }
        }.getOrThrow()

    private suspend fun getTopGames(
        page: Int
    ): GamesMainInfoListUi =
        runCatchingNonCancellation {
            asyncAwait(
                {
                    getTopGamesUseCase.invoke(page)
                }
            ) { result ->
                result.toUi()
            }
        }.getOrThrow()

    private suspend fun getGames(
        currentDate: String,
        endMonthDate: String,
        startDate: String
    ): HomeContentResult =
        runCatchingNonCancellation {
            asyncAwait(
                { getTopGamesUseCase.invoke(1) },
                { getGamesNewReleasesUseCase.invoke("$startDate,$currentDate", 1) },
                { getGameMonthReleasesUseCase.invoke("$currentDate,$endMonthDate", 1) },
            ) { top, released, nextReleases ->
                HomeContentResult(
                    top.toUi(),
                    released.toUi(),
                    nextReleases.toUi()
                )
            }
        }.getOrThrow()

}