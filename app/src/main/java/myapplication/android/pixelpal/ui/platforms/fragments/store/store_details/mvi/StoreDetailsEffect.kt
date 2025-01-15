package myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.mvi

import myapplication.android.pixelpal.ui.creator_details.mvi.CreatorDetailsEffect
import myapplication.android.pixelpal.ui.mvi.MviEffect

sealed interface StoreDetailsEffect: MviEffect {

    data object NavigateBack: StoreDetailsEffect

    data class OpenGameDetails(
        val gameId: Long,
        val genres: String,
        val name: String,
        val releaseDate: String,
        val image: String
    ): StoreDetailsEffect
}