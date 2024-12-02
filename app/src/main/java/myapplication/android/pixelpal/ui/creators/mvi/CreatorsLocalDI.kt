package myapplication.android.pixelpal.ui.creators.mvi

import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.data.repository.publishers.PublishersRepository
import myapplication.android.pixelpal.domain.usecase.creators.GetCreatorsUseCase
import myapplication.android.pixelpal.domain.usecase.publishers.GetPublishersUseCase
import javax.inject.Inject

class CreatorsLocalDI @Inject constructor(
    private val creatorsRepository: CreatorsRepository,
    private val publishersRepository: PublishersRepository
) {

    private val getCreatorsUseCase by lazy { GetCreatorsUseCase(creatorsRepository) }

    private val getPublishersUseCase by lazy { GetPublishersUseCase(publishersRepository) }

    val actor by lazy { CreatorsActor(getCreatorsUseCase, getPublishersUseCase) }

    val reducer by lazy { CreatorsReducer() }
}