package myapplication.android.pixelpal.data.source.pulisher

import myapplication.android.pixelpal.data.models.publishers.PublisherDetails
import myapplication.android.pixelpal.data.models.publishers.PublishersList

interface PublisherRemoteSource {

    suspend fun getPublisherDetails(id: Long): PublisherDetails

    suspend fun getPublishers(page: Int): PublishersList

}