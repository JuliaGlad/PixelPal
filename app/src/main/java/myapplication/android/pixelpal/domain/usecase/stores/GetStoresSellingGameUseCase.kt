package myapplication.android.pixelpal.domain.usecase.stores

import myapplication.android.pixelpal.data.repository.stores.StoresRepository
import myapplication.android.pixelpal.domain.mapper.store.toDomain
import myapplication.android.pixelpal.domain.model.stores.StoreSellingGameLinksDomainList
import javax.inject.Inject

class GetStoresSellingGameUseCase @Inject constructor(
    val storesRepository: StoresRepository
) {
    suspend fun invoke(gameId: String, page: Int): StoreSellingGameLinksDomainList =
        storesRepository.getStoresSellingGameLinks(gameId, page).toDomain()
}