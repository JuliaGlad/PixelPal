package myapplication.android.pixelpal.data.source.pulisher

import myapplication.android.pixelpal.data.database.entities.PublisherEntity
import myapplication.android.pixelpal.data.database.provider.PublisherProvider
import myapplication.android.pixelpal.data.models.publishers.Publisher
import myapplication.android.pixelpal.data.models.publishers.PublishersList
import java.util.stream.Collectors
import javax.inject.Inject

class PublishersLocalSourceImpl @Inject constructor(): PublisherLocalSource {
    override fun getPublishers(): PublishersList? {
        val data = PublisherProvider().getPublishers()
        return if (data.isNotEmpty()) {
            PublishersList(
                data.stream()
                    .map { it.toPublisher() }
                    .collect(Collectors.toList())
            )
        } else null
    }

    override fun deletePublisher() {
        PublisherProvider().deletePublishers()
    }

    override fun insertPublishers(publishersList: PublishersList) {
        PublisherProvider().insertPublishers(publishersList)
    }

    private fun PublisherEntity.toPublisher() = Publisher(gamesCount, image, publisherId, title)
}