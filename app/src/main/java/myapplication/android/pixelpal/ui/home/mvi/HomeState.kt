package myapplication.android.pixelpal.ui.home.mvi

import myapplication.android.pixelpal.ui.home.model.GamesNewsListUi
import myapplication.android.pixelpal.ui.mvi.MviState

data class HomeState(
    val ui: HomeLceState<HomeContentResult, GamesNewsListUi>,
    val topPage: Int = 1,
    val newReleasesPage: Int = 1,
    val nextReleasesPage: Int = 1
): MviState

sealed interface HomeLceState<out T, out R>{

    data object Loading: HomeLceState<Nothing, Nothing>

    data class Content<out T>(val data: T): HomeLceState<T, Nothing>

    data class UpdateNextReleasesContent<out R>(val data: R): HomeLceState<Nothing, R>

    data class UpdateReleasesContent<out R>(val data: R): HomeLceState<Nothing, R>

    data class UpdateTopContent<out R>(val data: R): HomeLceState<Nothing, R>

    data class Error(val throwable: Throwable): HomeLceState<Nothing, Nothing>
}