package myapplication.android.pixelpal.ui.publisher_details.mvi

import myapplication.android.pixelpal.ui.mvi.MviPartialState
import myapplication.android.pixelpal.ui.publisher_details.model.PublisherDetailsResult

sealed interface PublisherDetailsPartialState: MviPartialState {

    data object Loading: PublisherDetailsPartialState

    data class DataLoaded(val data: PublisherDetailsResult): PublisherDetailsPartialState

    data class Error(val throwable: Throwable): PublisherDetailsPartialState
}