package myapplication.android.pixelpal.domain.usecase.stores

import myapplication.android.pixelpal.data.repository.stores.StoresRepository
import myapplication.android.pixelpal.domain.mapper.store.toDomain
import myapplication.android.pixelpal.domain.model.stores.StoreDetailsDomain
import javax.inject.Inject

class GetStoreDetailsUseCase @Inject constructor(
    private val storesRepository: StoresRepository
) {
    suspend fun invoke(id: Int): StoreDetailsDomain =
        storesRepository.getStoreDetails(id).toDomain()
}