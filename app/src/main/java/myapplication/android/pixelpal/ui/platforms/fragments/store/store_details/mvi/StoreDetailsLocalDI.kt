package myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.mvi

import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.data.repository.stores.StoresRepository
import myapplication.android.pixelpal.domain.usecase.games.GetGamesByStoreUseCase
import myapplication.android.pixelpal.domain.usecase.stores.GetStoreDetailsUseCase
import javax.inject.Inject

class StoreDetailsLocalDI @Inject constructor(
    val storesRepository: StoresRepository,
    val gamesRepository: GamesRepository
) {
    private val getStoreDetailsUseCase by lazy { GetStoreDetailsUseCase(storesRepository) }

    private val getGamesByStoreUseCase by lazy { GetGamesByStoreUseCase(gamesRepository) }

    val actor by lazy { StoreDetailsActor(getStoreDetailsUseCase, getGamesByStoreUseCase) }

    val reducer by lazy { StoreDetailsReducer() }
}