package myapplication.android.pixelpal.ui.publisher_details.mvi

import myapplication.android.pixelpal.ui.mvi.MviEffect

sealed interface PublisherDetailsEffect: MviEffect {

    data object NavigateBack: PublisherDetailsEffect

}