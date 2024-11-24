package myapplication.android.pixelpal.domain.usecase.stores

import myapplication.android.pixelpal.data.repository.stores.StoresRepository
import myapplication.android.pixelpal.domain.model.stores.StoreDomainList

class GetStoresUseCase(
    val repository: StoresRepository
) {
    suspend fun invoke(): StoreDomainList = repository.getStores()
}