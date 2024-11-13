package myapplication.android.pixelpal.data.repository.stores

import myapplication.android.pixelpal.data.models.stores.StoresList
import myapplication.android.pixelpal.data.source.stores.StoresLocalSource
import myapplication.android.pixelpal.data.source.stores.StoresRemoteSource
import myapplication.android.pixelpal.domain.model.stores.StoreDomain
import myapplication.android.pixelpal.domain.wrapper.store.toDomain


class StoresRepositoryImpl(
    private val remoteSource: StoresRemoteSource,
    private val localSource: StoresLocalSource
) : StoresRepository {
    override suspend fun getStores(): List<StoreDomain> =
        (getLocalStores() ?: remoteSource.getStores()).toDomain()

    override fun getLocalStores(): StoresList? = null

}