package myapplication.android.pixelpal.data.source.pulisher

import myapplication.android.pixelpal.data.models.publishers.PublishersList

interface PublisherRemoteSource {

    suspend fun getPublishers(page: Int): PublishersList

}