package myapplication.android.pixelpal.data.repository.stores

import myapplication.android.pixelpal.data.source.stores.StoresLocalSource
import myapplication.android.pixelpal.data.source.stores.StoresRemoteSource
import myapplication.android.pixelpal.domain.model.stores.StoreDomainList
import myapplication.android.pixelpal.domain.wrapper.store.toDomain
import javax.inject.Inject

class StoresRepositoryImpl @Inject constructor(
    private val localSource: StoresLocalSource,
    private val remoteSource: StoresRemoteSource
) : StoresRepository {

    override suspend fun getStores(page: Int): StoreDomainList {
        val local = localSource.getStores(page)
        val result =
            if (local != null) local
            else {
                val remote = remoteSource.getStores(page)
                localSource.insertStores(page, remote)
                remote
            }.toDomain()
        return result
    }
}