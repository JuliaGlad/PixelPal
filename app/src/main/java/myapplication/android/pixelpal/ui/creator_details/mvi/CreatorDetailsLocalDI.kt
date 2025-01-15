package myapplication.android.pixelpal.ui.creator_details.mvi

import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.domain.usecase.creators.GetCreatorDetailsUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGameByCreatorUseCase
import javax.inject.Inject

class CreatorDetailsLocalDI @Inject constructor(
    val creatorsRepository: CreatorsRepository,
    val gamesRepository: GamesRepository
) {
    private val getGameByCreatorUseCase by lazy { GetGameByCreatorUseCase(gamesRepository) }

    private val getCreatorDetailsUseCase by lazy { GetCreatorDetailsUseCase(creatorsRepository) }

    val actor by lazy { CreatorDetailsActor(getCreatorDetailsUseCase, getGameByCreatorUseCase) }

    val reducer by lazy { CreatorDetailsReducer() }
}