package myapplication.android.pixelpal.ui.game_details.mvi

import myapplication.android.pixelpal.ui.creator_details.mvi.CreatorDetailsEffect
import myapplication.android.pixelpal.ui.mvi.MviEffect

sealed interface GameDetailsEffect: MviEffect {

    data object NavigateBack: GameDetailsEffect

    data class OpenAllSameSeries(val gameId: Long): GameDetailsEffect

    data class OpenAllAdditionsAndParentGames(val gameId: Long): GameDetailsEffect

    data class OpenAllCreators(val gameId: Long): GameDetailsEffect

    data class OpenCreatorDetails(
        val creatorId: Long,
        val name: String,
        val role: Array<String?>,
        val famousProjects: Int,
        val image: String?
    ): GameDetailsEffect

    data class OpenGameDetails(
        val gameId: Long,
        val genres: String,
        val name: String,
        val releaseDate: String,
        val image: String
    ): GameDetailsEffect
}