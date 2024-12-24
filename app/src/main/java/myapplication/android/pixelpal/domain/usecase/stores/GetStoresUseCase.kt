package myapplication.android.pixelpal.domain.usecase.stores

import myapplication.android.pixelpal.data.repository.stores.StoresRepository
import myapplication.android.pixelpal.domain.model.stores.StoreDomainList
import javax.inject.Inject

class GetStoresUseCase @Inject constructor(
    val repository: StoresRepository
) {
    suspend fun invoke(page: Int): StoreDomainList = repository.getStores(page)
}