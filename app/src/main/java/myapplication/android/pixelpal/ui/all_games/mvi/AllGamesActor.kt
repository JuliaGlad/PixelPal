package myapplication.android.pixelpal.ui.all_games.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.pixelpal.domain.usecase.games.GetGameAdditionsUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGameByCreatorUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGameByPublisherUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGameMonthReleasesUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGamesByPlatformUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGamesByStoreUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGamesFromSameSeriesUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGamesNewReleasesUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetParenGamesUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetTopGamesUseCase
import myapplication.android.pixelpal.ui.games.games.model.GamesShortDataUi
import myapplication.android.pixelpal.ui.games.games.model.GamesShortDataUiList
import myapplication.android.pixelpal.ui.games.games.model.toUi
import myapplication.android.pixelpal.ui.home.model.GamesMainInfoListUi
import myapplication.android.pixelpal.ui.home.model.toUi
import myapplication.android.pixelpal.ui.ktx.asyncAwait
import myapplication.android.pixelpal.ui.ktx.runCatchingNonCancellation
import myapplication.android.pixelpal.ui.mvi.MviActor

class AllGamesActor(
    private val getTopGamesUseCase: GetTopGamesUseCase,
    private val getGamesNewReleasesUseCase: GetGamesNewReleasesUseCase,
    private val getGameMonthReleasesUseCase: GetGameMonthReleasesUseCase,
    private val getParenGamesUseCase: GetParenGamesUseCase,
    private val getGameAdditionsUseCase: GetGameAdditionsUseCase,
    private val getGamesFromSameSeriesUseCase: GetGamesFromSameSeriesUseCase,
    private val getGamesByCreatorUseCase: GetGameByCreatorUseCase,
    private val getGamesByPlatformUseCase: GetGamesByPlatformUseCase,
    private val getGamesByStoreUseCase: GetGamesByStoreUseCase,
    private val getGameByPublisherUseCase: GetGameByPublisherUseCase
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

            is AllGamesIntent.GetAllSameSeries -> loadGamesFromSameSeries(
                intent.gameId,
                state.page + 1
            )

            is AllGamesIntent.GetGameParentSeries ->
                loadParenAndAdditions(intent.gameId, state.page + 1)

            is AllGamesIntent.GetCreatorGames -> loadCreatorGames(intent.creatorId, state.page + 1)
            is AllGamesIntent.GetGamesByPlatform -> loadGamesByPlatform(
                intent.platformId,
                state.page + 1
            )

            is AllGamesIntent.GetGamesByStore -> loadGamesByStore(intent.storeId, state.page + 1)
            is AllGamesIntent.GetGamesByPublisher -> loadGamesByPublisher(intent.publisherId, state.page + 1)
        }

    private fun init() = flow { emit(AllGamesPartialState.Init) }

    private fun loadCreatorGames(creatorId: Long, page: Int) =
        flow {
            kotlin.runCatching {
                getCreatorGames(creatorId, page)
            }.fold(
                onSuccess = { data ->
                    emit(AllGamesPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(AllGamesPartialState.Error(throwable))
                }
            )
        }

    private fun loadGamesFromSameSeries(gameId: Long, page: Int) =
        flow {
            kotlin.runCatching {
                getGamesFromSameSeries(gameId, page)
            }.fold(
                onSuccess = { data ->
                    emit(AllGamesPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(AllGamesPartialState.Error(throwable))
                }
            )
        }

    private fun loadParenAndAdditions(gameId: Long, page: Int) =
        flow {
            kotlin.runCatching {
                getParentGameAndAdditions(gameId, page)
            }.fold(
                onSuccess = { data ->
                    emit(AllGamesPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(AllGamesPartialState.Error(throwable))
                }
            )
        }

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

    private fun loadGamesByPlatform(
        platformId: Long,
        page: Int
    ) = flow {
        kotlin.runCatching {
            getGamesByPlatform(platformId, page)
        }.fold(
            onSuccess = { data ->
                emit(AllGamesPartialState.DataLoaded(data))
            },
            onFailure = { throwable ->
                emit(AllGamesPartialState.Error(throwable))
            }
        )
    }

    private fun loadGamesByStore(
        storeId: Int,
        page: Int
    ) =
        flow {
            kotlin.runCatching {
                getGamesByStore(storeId, page)
            }.fold(
                onSuccess = { data ->
                    emit(AllGamesPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(AllGamesPartialState.Error(throwable))
                }
            )
        }

    private fun loadGamesByPublisher(
        publisherId: Long,
        page: Int
    ) =
        flow {
            kotlin.runCatching {
                getGamesByPublisher(publisherId, page)
            }.fold(
                onSuccess = {data ->
                    emit(AllGamesPartialState.DataLoaded(data))
                },
                onFailure = {throwable ->
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

    private suspend fun getGamesByPublisher(
        publisherId: Long,
        page: Int
    ) = runCatchingNonCancellation {
        asyncAwait(
            { getGameByPublisherUseCase.invoke(publisherId, page) }
        ){ data ->
            data.toUi()
        }
    }.getOrThrow()

    private suspend fun getGamesFromSameSeries(
        gameId: Long,
        page: Int
    ): GamesShortDataUiList =
        runCatchingNonCancellation {
            asyncAwait(
                { getGamesFromSameSeriesUseCase.invoke(gameId.toString(), page) }
            ) { data ->
                data.toUi()
            }
        }.getOrThrow()


    private suspend fun getParentGameAndAdditions(
        gameId: Long,
        page: Int
    ): GamesShortDataUiList =
        runCatchingNonCancellation {
            asyncAwait(
                { getGameAdditionsUseCase.invoke(gameId.toString(), page) },
                { getParenGamesUseCase.invoke(gameId.toString(), page) }
            ) { additions, parent ->
                val items: MutableList<GamesShortDataUi> = mutableListOf()
                items.addAll(additions.toUi().items)
                items.addAll(parent.toUi().items)
                GamesShortDataUiList(items)
            }
        }.getOrThrow()

    private suspend fun getGamesByStore(storeId: Int, page: Int) =
        runCatchingNonCancellation {
            asyncAwait(
                { getGamesByStoreUseCase.invoke(storeId, page) }
            ) { result ->
                result.toUi()
            }
        }.getOrThrow()

    private suspend fun getGamesByPlatform(
        platformId: Long,
        page: Int
    ) = runCatchingNonCancellation {
        asyncAwait(
            { getGamesByPlatformUseCase.invoke(platformId, page) }
        ) { result ->
            result.toUi()
        }
    }.getOrThrow()

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

    private suspend fun getCreatorGames(creatorId: Long, page: Int) =
        runCatchingNonCancellation {
            asyncAwait(
                { getGamesByCreatorUseCase.invoke(creatorId, page) }
            ) { games ->
                games.toUi()
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