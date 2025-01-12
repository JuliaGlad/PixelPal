package myapplication.android.pixelpal.ui.creator_details.mvi

import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.domain.usecase.creators.GetCreatorDetailsUseCase
import javax.inject.Inject

class CreatorDetailsLocalDI @Inject constructor(
    val creatorsRepository: CreatorsRepository
) {
    private val getCreatorDetailsUseCase by lazy { GetCreatorDetailsUseCase(creatorsRepository) }

    val actor by lazy { CreatorDetailsActor(getCreatorDetailsUseCase) }

    val reducer by lazy { CreatorDetailsReducer() }
}