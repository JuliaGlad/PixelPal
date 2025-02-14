package myapplication.android.pixelpal.ui.game_details.mvi

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.pixelpal.domain.usecase.creators.GetGameCreatorsUseCase
import myapplication.android.pixelpal.domain.usecase.favorite_games.AddFavoriteGamesUseCase
import myapplication.android.pixelpal.domain.usecase.favorite_games.RemoveFavoriteGameUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGameAdditionsUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGameDescriptionUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGameScreenshotsUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGamesFromSameSeriesUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetParenGamesUseCase
import myapplication.android.pixelpal.domain.usecase.stores.GetStoresSellingGameUseCase
import myapplication.android.pixelpal.ui.game_details.model.mapper.toUi
import myapplication.android.pixelpal.ui.games.games.model.toUi
import myapplication.android.pixelpal.ui.ktx.asyncAwait
import myapplication.android.pixelpal.ui.ktx.runCatchingNonCancellation
import myapplication.android.pixelpal.ui.mvi.MviActor

class GameDetailsActor(
    private val getStoresSellingGameUseCase: GetStoresSellingGameUseCase,
    private val getGameCreatorUseCase: GetGameCreatorsUseCase,
    private val getGameAdditionsUseCase: GetGameAdditionsUseCase,
    private val getGameDescriptionUseCase: GetGameDescriptionUseCase,
    private val getGameScreenshotsUseCase: GetGameScreenshotsUseCase,
    private val getParentGamesUseCase: GetParenGamesUseCase,
    private val getGamesFromSameSeriesUseCase: GetGamesFromSameSeriesUseCase,
    private val addFavoriteGamesUseCase: AddFavoriteGamesUseCase,
    private val removeFavoriteGameUseCase: RemoveFavoriteGameUseCase
) : MviActor<GameDetailsPartialState, GameDetailsIntent, GameDetailsState, GameDetailsEffect>() {
    override fun resolve(
        intent: GameDetailsIntent,
        state: GameDetailsState
    ): Flow<GameDetailsPartialState> =
        when (intent) {
            is GameDetailsIntent.GetCreators -> loadCreators(intent.gameId, state.creatorsPage + 1)
            is GameDetailsIntent.GetGameMainData -> loadMainData(intent.gameId)
            is GameDetailsIntent.GetParentGamesAndAdditions ->
                loadParentAndAddition(intent.gameId, state.parentsAndAdditionPage + 1)
            is GameDetailsIntent.GetSameSeries -> loadSameSeries(intent.gameId, state.seriesPage + 1)
            GameDetailsIntent.Init -> updateInit()
            is GameDetailsIntent.GetStoresSellingGame -> loadStores(intent.gameId, state.storesPage + 1)
            is GameDetailsIntent.AddGameToFavorites -> addFavoriteGame(intent.gameId)
            is GameDetailsIntent.RemoveGameFromFavorites -> removeFavoriteGame(intent.gameId)
        }

    private fun loadParentAndAddition(gameId: String, page: Int) =
        flow {
            kotlin.runCatching {
               getParentAndAdditions(gameId, page)
            }.fold(
                onSuccess = { data ->
                    emit(GameDetailsPartialState.UpdateParentGamesAndAddition(data.first, data.second))
                },
                onFailure = { throwable ->
                    emit(GameDetailsPartialState.UpdateError(throwable))
                }
            )
        }

    private fun loadStores(gameId: String, page: Int) =
        flow {
            kotlin.runCatching {
                getStoresSellingGame(gameId, page)
            }.fold(
                onSuccess = { data ->
                    emit(GameDetailsPartialState.UpdateStores(data))
                },
                onFailure = { throwable ->
                    emit(GameDetailsPartialState.UpdateError(throwable))
                }
            )
        }

    private fun loadSameSeries(gameId: String, page: Int) =
        flow {
            kotlin.runCatching {
                getGamesFromSameSeries(gameId, page)
            }.fold(
                onSuccess = { data ->
                    emit(GameDetailsPartialState.UpdateSameSeries(data))
                },
                onFailure = { throwable ->
                    emit(GameDetailsPartialState.UpdateError(throwable))
                }
            )
        }

    private fun loadCreators(gameId: String, page: Int) =
        flow {
            kotlin.runCatching {
                getCreators(gameId, page)
            }.fold(
                onSuccess = { data ->
                    emit(GameDetailsPartialState.UpdateCreators(data))
                },
                onFailure = { throwable ->
                    emit(GameDetailsPartialState.UpdateError(throwable))
                }
            )
        }

    private fun loadMainData(gameId: String)=
        flow {
            kotlin.runCatching {
                val pair = getParentAndAdditions(gameId, 1)
                GameDetailsContentResult(
                    getStoresSellingGame(gameId, 1),
                    pair.first,
                    pair.second,
                    getGamesFromSameSeries(gameId, 1),
                    getCreators(gameId, 1),
                    getGameScreenshots(gameId),
                    getGameDescription(gameId.toLong())
                )
            }.fold(
                onSuccess = { data ->
                    emit(GameDetailsPartialState.UpdateDataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(GameDetailsPartialState.UpdateError(throwable))
                }
            )
        }

    private fun removeFavoriteGame(gameId: Long) =
        flow {
            kotlin.runCatching {
                removeFavoriteGameUseCase.invoke(gameId)
            }.fold(
                onSuccess = {
                    emit(GameDetailsPartialState.GameRemoved)
                },
                onFailure = { throwable ->
                    emit(GameDetailsPartialState.UpdateError(throwable))
                }
            )
        }

    private fun addFavoriteGame(gameId: Long) =
        flow {
            kotlin.runCatching {
               // runCatchingNonCancellation {
               //     coroutineScope {
                        addFavoriteGamesUseCase.invoke(gameId)
             //       }
            //    }.getOrThrow()
            }.fold(
                onSuccess = {
                    emit(GameDetailsPartialState.GameAdded)
                },
                onFailure = { throwable ->
                    emit(GameDetailsPartialState.UpdateError(throwable))
                }
            )
        }

    private suspend fun getStoresSellingGame(gameId: String, page: Int) =
        runCatchingNonCancellation {
            asyncAwait(
                { getStoresSellingGameUseCase.invoke(gameId, page) },
            ) { stores ->
                stores.toUi()
            }
        }.getOrThrow()

    private suspend fun getParentAndAdditions(gameId: String, page: Int) =
        runCatchingNonCancellation {
            asyncAwait(
                { getParentGamesUseCase.invoke(gameId, page) },
                { getGameAdditionsUseCase.invoke(gameId, page) }
            ) { parentGames, additions ->
                Pair(parentGames.toUi(), additions.toUi())
            }
        }.getOrThrow()

    private suspend fun getGamesFromSameSeries(gameId: String, page: Int) =
        runCatchingNonCancellation {
            asyncAwait(
                { getGamesFromSameSeriesUseCase.invoke(gameId, page) }
            ) { games ->
                games.toUi()
            }
        }.getOrThrow()


    private suspend fun getGameScreenshots(gameId: String) =
        runCatchingNonCancellation {
            asyncAwait(
                { getGameScreenshotsUseCase.invoke(gameId) }
            ) { screenshots ->
                screenshots.toUi()
            }
        }.getOrThrow()


    private suspend fun getGameDescription(gameId: Long) =
        runCatchingNonCancellation {
            asyncAwait(
                { getGameDescriptionUseCase.invoke(gameId) }
            ) { description ->
                description.toUi()
            }
        }.getOrThrow()

    private suspend fun getCreators(gameId: String, page: Int) =
        runCatchingNonCancellation {
            asyncAwait(
                { getGameCreatorUseCase.invoke(gameId, page) }
            ) { creators ->
                creators.toUi()
            }
        }.getOrThrow()

    private fun updateInit() = flow { emit(GameDetailsPartialState.UpdateLoading) }
}