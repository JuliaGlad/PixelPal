package myapplication.android.pixelpal.ui.delegates.delegates.releases

data class ReleasesModel(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val genres: String,
    val uri: String,
    val listener: myapplication.android.pixelpal.ui.listener.ClickListener
)