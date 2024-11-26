package myapplication.android.pixelpal.ui.games.games.recycler_view

sealed interface LayoutType {

    data object OneItem: LayoutType

    data object Linear: LayoutType

    data object Grid: LayoutType
}