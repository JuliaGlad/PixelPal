package myapplication.android.pixelpal.ui.publisher_details.mvi

import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.data.repository.publishers.PublishersRepository
import myapplication.android.pixelpal.domain.usecase.games.GetGameByPublisherUseCase
import myapplication.android.pixelpal.domain.usecase.publishers.GetPublisherDetailsUseCase
import javax.inject.Inject

class PublisherDetailsLocalDI @Inject constructor(
    private val publishersRepository: PublishersRepository,
    private val gamesRepository: GamesRepository
) {
    private val getGameByPublisherUseCase by lazy { GetGameByPublisherUseCase(gamesRepository) }

    private val getPublisherDetailsUseCase by lazy { GetPublisherDetailsUseCase(publishersRepository) }

    val actor by lazy { PublisherDetailsActor(getPublisherDetailsUseCase, getGameByPublisherUseCase) }

    val reducer by lazy { PublisherDetailsReducer() }
}