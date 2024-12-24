package myapplication.android.pixelpal.data.source.pulisher

import myapplication.android.pixelpal.data.models.publishers.PublishersList

interface PublisherLocalSource {

    fun getPublishers(page: Int): PublishersList?

    fun deletePublisher()

    fun insertPublishers(currentPage: Int, publishersList: PublishersList)
}