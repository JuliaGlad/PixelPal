package myapplication.android.pixelpal.ui.creator_details.mvi

import myapplication.android.pixelpal.ui.mvi.MviIntent

sealed interface CreatorDetailsIntent: MviIntent {

    data object Init: CreatorDetailsIntent

    data class GetCreatorDetails(val id: Long): CreatorDetailsIntent
}