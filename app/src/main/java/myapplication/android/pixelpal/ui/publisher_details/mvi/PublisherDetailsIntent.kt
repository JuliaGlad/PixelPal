package myapplication.android.pixelpal.ui.publisher_details.mvi

import myapplication.android.pixelpal.ui.mvi.MviIntent

sealed interface PublisherDetailsIntent: MviIntent {

    data object Init: PublisherDetailsIntent

    data class GetPublisher(
        val id: Long
    ): PublisherDetailsIntent
}