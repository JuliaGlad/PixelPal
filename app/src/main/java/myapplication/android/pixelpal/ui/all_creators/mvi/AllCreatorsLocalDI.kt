package myapplication.android.pixelpal.ui.all_creators.mvi

import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.domain.usecase.creators.GetGameCreatorsUseCase
import javax.inject.Inject

class AllCreatorsLocalDI @Inject constructor(
    val creatorsRepository: CreatorsRepository
) {
    private val getGameCreatorsUseCase by lazy { GetGameCreatorsUseCase(creatorsRepository) }

    val actor by lazy { AllCreatorsActor(getGameCreatorsUseCase) }

    val reducer by lazy { AllCreatorsReducer() }
}