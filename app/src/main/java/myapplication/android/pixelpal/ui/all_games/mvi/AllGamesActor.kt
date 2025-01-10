package myapplication.android.pixelpal.ui.all_games.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.pixelpal.domain.usecase.games.GetGameMonthReleasesUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGamesNewReleasesUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetTopGamesUseCase
import myapplication.android.pixelpal.ui.home.model.GamesNewsListUi
import myapplication.android.pixelpal.ui.home.model.toUi
import myapplication.android.pixelpal.ui.ktx.asyncAwait
import myapplication.android.pixelpal.ui.ktx.runCatchingNonCancellation
import myapplication.android.pixelpal.ui.mvi.MviActor

class AllGamesActor(
    private val getTopGamesUseCase: GetTopGamesUseCase,
    private val getGamesNewReleasesUseCase: GetGamesNewReleasesUseCase,
    private val getGameMonthReleasesUseCase: GetGameMonthReleasesUseCase
) : MviActor<
        AllGamesPartialState,
        AllGamesIntent,
        AllGamesState,
        AllGamesEffect>() {
    override fun resolve(intent: AllGamesIntent, state: AllGamesState): Flow<AllGamesPartialState> =
        when (intent) {
            AllGamesIntent.GetGames -> loadData(state.page + 1)
            AllGamesIntent.Init -> init()
            is AllGamesIntent.GetAllCurrentReleases ->
                loadCurrentReleases(
                    intent.startDate,
                    intent.currentDate,
                    state.page + 1
                )

            is AllGamesIntent.GetAllNextReleases ->
                loadNextReleases(
                    intent.endDate,
                    intent.currentDate,
                    state.page + 1
                )
        }

    private fun init() = flow { emit(AllGamesPartialState.Init) }

    private fun loadData(page: Int) =
        flow {
            kotlin.runCatching {
                getGames(page)
            }.fold(
                onSuccess = { data ->
                    emit(AllGamesPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(AllGamesPartialState.Error(throwable))
                }
            )
        }

    private fun loadCurrentReleases(
        startDate: String,
        currentDate: String,
        page: Int
    ) =
        flow {
            kotlin.runCatching {
                getReleasesGames("$startDate,$currentDate", page)
            }.fold(
                onSuccess = { data ->
                    emit(
                        AllGamesPartialState.DataLoaded(data)
                    )
                },
                onFailure = { throwable ->
                    emit(AllGamesPartialState.Error(throwable))
                }
            )
        }

    private fun loadNextReleases(
        endMonthDate: String,
        currentDate: String,
        page: Int
    ) =
        flow {
            kotlin.runCatching {
                getNextReleasesGames("$currentDate,$endMonthDate", page)
            }.fold(
                onSuccess = { data ->
                    emit(AllGamesPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(AllGamesPartialState.Error(throwable))
                }
            )
        }

    private suspend fun getReleasesGames(
        date: String,
        page: Int
    ): GamesNewsListUi =
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
    ): GamesNewsListUi =
        runCatchingNonCancellation {
            asyncAwait(
                { getGameMonthReleasesUseCase.invoke(date, page) }
            ) { result ->
                result.toUi()
            }
        }.getOrThrow()

    private suspend fun getGames(page: Int) =
        runCatchingNonCancellation {
            asyncAwait(
                { getTopGamesUseCase.invoke(page) }
            ) { games ->
                games.toUi()
            }
        }.getOrThrow()
}