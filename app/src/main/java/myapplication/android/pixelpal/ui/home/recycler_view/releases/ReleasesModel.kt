package myapplication.android.pixelpal.ui.home.recycler_view.releases

data class ReleasesModel(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val genres: String,
    val uri: String,
    val listener: myapplication.android.pixelpal.ui.listener.ClickListener
)