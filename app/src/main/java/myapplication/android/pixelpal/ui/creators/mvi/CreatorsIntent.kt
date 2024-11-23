package myapplication.android.pixelpal.ui.creators.mvi

import myapplication.android.pixelpal.ui.mvi.MviIntent

sealed interface CreatorsIntent: MviIntent {

    data object Init: CreatorsIntent

    data class GetRolesCreators(val roleId: Int): CreatorsIntent
}