package myapplication.android.pixelpal.data.source.pulisher

import myapplication.android.pixelpal.data.models.publishers.PublishersList

interface PublisherLocalSource {

    fun getPublishers(): PublishersList?

    fun deletePublisher()

    fun insertPublishers(publishersList: PublishersList)
}