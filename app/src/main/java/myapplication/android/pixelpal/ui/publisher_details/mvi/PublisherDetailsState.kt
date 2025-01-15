package myapplication.android.pixelpal.ui.publisher_details.mvi

import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviState
import myapplication.android.pixelpal.ui.publisher_details.model.PublisherDetailsResult

data class PublisherDetailsState(val ui: LceState<PublisherDetailsResult>, var page: Int = 0): MviState