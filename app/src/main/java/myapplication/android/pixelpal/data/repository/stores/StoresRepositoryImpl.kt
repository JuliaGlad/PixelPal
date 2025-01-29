package myapplication.android.pixelpal.data.repository.stores

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import myapplication.android.pixelpal.data.repository.dto.store.StoreDetailsDto
import myapplication.android.pixelpal.data.repository.dto.store.StoreDtoList
import myapplication.android.pixelpal.data.repository.dto.store.StoresSellingGameLinksDtoList
import myapplication.android.pixelpal.data.repository.mapper.store.toDto
import myapplication.android.pixelpal.data.source.stores.StoresLocalSource
import myapplication.android.pixelpal.data.source.stores.StoresRemoteSource
import javax.inject.Inject

class StoresRepositoryImpl @Inject constructor(
    private val localSource: StoresLocalSource,
    private val remoteSource: StoresRemoteSource
) : StoresRepository {
    override suspend fun getStoreDetails(id: Int): StoreDetailsDto =
        withContext(Dispatchers.IO) {
            remoteSource.getStoresDetails(id).toDto()
        }

    override suspend fun getStoresSellingGameLinks(
        gameId: String,
        page: Int
    ): StoresSellingGameLinksDtoList =
        withContext(Dispatchers.IO) {
            remoteSource.getStoresSellingGame(gameId, page).toDto()
        }

    override suspend fun getStores(page: Int): StoreDtoList {
        val local = localSource.getStores(page)
        val result =
            if (local != null) local
            else {
                val remote = withContext(Dispatchers.IO) {
                    remoteSource.getStores(page)
                }
                localSource.insertStores(page, remote)
                remote
            }
        return result.toDto()
    }
}