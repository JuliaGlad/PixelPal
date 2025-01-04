package myapplication.android.pixelpal.ui.game_details.model

class ScreenshotsUiList(
    val items: List<ScreenshotUi>
): GameDetailsResult

class ScreenshotUi(
    val id: Long,
    val image: String
)