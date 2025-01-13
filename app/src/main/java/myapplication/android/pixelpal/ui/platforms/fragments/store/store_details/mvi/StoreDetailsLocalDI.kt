package myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.mvi

import myapplication.android.pixelpal.data.repository.stores.StoresRepository
import myapplication.android.pixelpal.domain.usecase.stores.GetStoreDetailsUseCase
import javax.inject.Inject

class StoreDetailsLocalDI @Inject constructor(
    val storesRepository: StoresRepository
) {
    private val getStoreDetailsUseCase by lazy { GetStoreDetailsUseCase(storesRepository) }

    val actor by lazy { StoreDetailsActor(getStoreDetailsUseCase) }

    val reducer by lazy { StoreDetailsReducer() }
}