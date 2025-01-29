package myapplication.android.pixelpal.ui.publisher_details.model

import myapplication.android.pixelpal.domain.model.publishers.PublisherDomainDetails

fun PublisherDomainDetails.toUi() =
    PublisherDetailsModelUi(
        description
    )