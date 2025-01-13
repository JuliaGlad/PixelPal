package myapplication.android.pixelpal.ui.platforms.fragments.store.mvi

import myapplication.android.pixelpal.ui.mvi.MviEffect

sealed interface StoresEffect : MviEffect {

    data class OpenStoresDetailsScreen(
        val id: Int,
        val name: String,
        val image: String,
        val domain: String?,
        val projects: Int?
    ) : StoresEffect

}