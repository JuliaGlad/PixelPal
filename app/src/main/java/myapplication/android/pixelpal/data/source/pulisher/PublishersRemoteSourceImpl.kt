package myapplication.android.pixelpal.data.source.pulisher

import myapplication.android.pixelpal.data.api.GamesApi
import myapplication.android.pixelpal.data.models.publishers.PublishersList
import javax.inject.Inject

class PublishersRemoteSourceImpl @Inject constructor(
    private val api: GamesApi
): PublisherRemoteSource{
    override suspend fun getPublishers(page: Int): PublishersList =
        api.getPublishers(page)
}