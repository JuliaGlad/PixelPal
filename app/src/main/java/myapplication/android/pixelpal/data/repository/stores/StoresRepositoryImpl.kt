package myapplication.android.pixelpal.data.repository.stores

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import myapplication.android.pixelpal.data.source.stores.StoresLocalSource
import myapplication.android.pixelpal.data.source.stores.StoresRemoteSource
import myapplication.android.pixelpal.domain.model.stores.StoreDetailsDomain
import myapplication.android.pixelpal.domain.model.stores.StoreDomainList
import myapplication.android.pixelpal.domain.model.stores.StoreSellingGameLinksDomainList
import myapplication.android.pixelpal.domain.wrapper.store.toDomain
import javax.inject.Inject

class StoresRepositoryImpl @Inject constructor(
    private val localSource: StoresLocalSource,
    private val remoteSource: StoresRemoteSource
) : StoresRepository {
    override suspend fun getStoreDetails(id: Int): StoreDetailsDomain =
        withContext(Dispatchers.IO) {
            remoteSource.getStoresDetails(id).toDomain()
        }

    override suspend fun getStoresSellingGameLinks(
        gameId: String,
        page: Int
    ): StoreSellingGameLinksDomainList =
        withContext(Dispatchers.IO) {
            remoteSource.getStoresSellingGame(gameId, page).toDomain()
        }

    override suspend fun getStores(page: Int): StoreDomainList {
        val local = localSource.getStores(page)
        val result =
            if (local != null) local
            else {
                val remote = withContext(Dispatchers.IO) {
                    remoteSource.getStores(page)
                }
                localSource.insertStores(page, remote)
                remote
            }.toDomain()
        return result
    }
}