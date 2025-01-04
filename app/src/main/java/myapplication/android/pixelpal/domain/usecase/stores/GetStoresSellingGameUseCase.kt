package myapplication.android.pixelpal.domain.usecase.stores

import myapplication.android.pixelpal.data.repository.stores.StoresRepository
import javax.inject.Inject

class GetStoresSellingGameUseCase @Inject constructor(
    val storesRepository: StoresRepository
) {
    suspend fun invoke(gameId: String, page: Int) = storesRepository.getStoresSellingGameLinks(gameId, page)
}