package myapplication.android.pixelpal.ui.game_details.model

class StoresSellingGameUiList(
    val items: List<StoreLinksUi>
): GameDetailsResult

class StoreLinksUi (
    val id: Long,
    val url: String
)