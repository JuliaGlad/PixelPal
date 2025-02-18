package myapplication.android.pixelpal.ui.platforms.fragments.store.mvi

import myapplication.android.pixelpal.data.repository.stores.StoresRepository
import myapplication.android.pixelpal.domain.usecase.stores.GetStoresByQueryUseCase
import myapplication.android.pixelpal.domain.usecase.stores.GetStoresUseCase
import javax.inject.Inject

class StoresLocalDI @Inject constructor(
    private val storeRepository: StoresRepository
) {
    private val getStoresByQueryUseCase by lazy { GetStoresByQueryUseCase(storeRepository) }

    private val getStoresUseCase by lazy { GetStoresUseCase(storeRepository) }

    val actor by lazy { StoresActor(getStoresUseCase, getStoresByQueryUseCase) }

    val reducer by lazy { StoresReducer() }
}