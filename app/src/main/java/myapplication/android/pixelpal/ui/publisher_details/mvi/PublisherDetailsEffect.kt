package myapplication.android.pixelpal.ui.publisher_details.mvi

import myapplication.android.pixelpal.ui.creator_details.mvi.CreatorDetailsEffect
import myapplication.android.pixelpal.ui.mvi.MviEffect

sealed interface PublisherDetailsEffect: MviEffect {

    data object NavigateBack: PublisherDetailsEffect

    data class OpenGameDetails(
        val gameId: Long,
        val genres: String,
        val name: String,
        val releaseDate: String,
        val image: String
    ): PublisherDetailsEffect
}