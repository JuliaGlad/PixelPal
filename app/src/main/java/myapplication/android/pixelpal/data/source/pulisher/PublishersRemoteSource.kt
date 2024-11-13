package myapplication.android.pixelpal.data.source.pulisher

import myapplication.android.pixelpal.data.api.GamesApi
import myapplication.android.pixelpal.data.models.publishers.PublishersList

class PublishersRemoteSource(
    private val api: GamesApi
){
    suspend fun getPublishers(): PublishersList =
        api.getPublishers()
}