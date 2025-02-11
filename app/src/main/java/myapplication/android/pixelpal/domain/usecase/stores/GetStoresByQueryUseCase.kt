package myapplication.android.pixelpal.domain.usecase.stores

import myapplication.android.pixelpal.data.repository.stores.StoresRepository
import myapplication.android.pixelpal.domain.mapper.store.toDomain
import javax.inject.Inject

class GetStoresByQueryUseCase @Inject constructor(
    private val storesRepository: StoresRepository
) {
    suspend fun invoke(page: Int, query: String) =
        storesRepository.getStores(page).toDomain(query)
}