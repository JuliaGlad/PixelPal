package myapplication.android.pixelpal.ui.publisher_details.mvi

import myapplication.android.pixelpal.data.repository.publishers.PublishersRepository
import myapplication.android.pixelpal.domain.usecase.publishers.GetPublisherDetailsUseCase
import javax.inject.Inject

class PublisherDetailsLocalDI @Inject constructor(
    private val publishersRepository: PublishersRepository
) {
    private val getPublisherDetailsUseCase by lazy { GetPublisherDetailsUseCase(publishersRepository) }

    val actor by lazy { PublisherDetailsActor(getPublisherDetailsUseCase) }

    val reducer by lazy { PublisherDetailsReducer() }
}