package myapplication.android.pixelpal.ui.creators.mvi

import myapplication.android.pixelpal.ui.mvi.MviIntent

sealed interface CreatorsIntent: MviIntent {

    data object Init: CreatorsIntent

    data object GetPublishers: CreatorsIntent

    data class Search(val query: String): CreatorsIntent

    data class GetRolesCreators(val roleId: Int): CreatorsIntent
}