package myapplication.android.pixelpal.ui.platforms.fragments.platform.model

class PlatformUiList(
    val list: List<PlatformsUi>
)

class PlatformsUi(
    val id: Long,
    val name: String,
    val gamesCount: Int,
    val startYear: Int?,
    val background: String
)