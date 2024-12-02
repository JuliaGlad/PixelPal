package myapplication.android.pixelpal.data.repository.stores

import myapplication.android.pixelpal.data.models.stores.StoresList
import myapplication.android.pixelpal.data.source.stores.StoresLocalSource
import myapplication.android.pixelpal.data.source.stores.StoresLocalSourceImpl
import myapplication.android.pixelpal.data.source.stores.StoresRemoteSource
import myapplication.android.pixelpal.data.source.stores.StoresRemoteSourceImpl
import myapplication.android.pixelpal.domain.model.stores.StoreDomainList
import myapplication.android.pixelpal.domain.wrapper.store.toDomain
import javax.inject.Inject


class StoresRepositoryImpl @Inject constructor(
    private val localSource: StoresLocalSource,
    private val remoteSource: StoresRemoteSource
) : StoresRepository {
    override suspend fun getStores(): StoreDomainList =
        (getLocalStores() ?: remoteSource.getStores()).toDomain()

    override fun getLocalStores(): StoresList? = null

}