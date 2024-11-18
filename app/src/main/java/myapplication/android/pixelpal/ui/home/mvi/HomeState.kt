package myapplication.android.pixelpal.ui.home.mvi

import myapplication.android.pixelpal.ui.home.model.GamesNewsUi
import myapplication.android.pixelpal.ui.mvi.MviState

data class HomeState(val ui: LceState<HomeContentResult>): MviState

sealed interface LceState<out T>{

    data object Loading: LceState<Nothing>

    data class Content<out T>(val data: T): LceState<T>

    data class Error(val throwable: Throwable): LceState<Nothing>

}