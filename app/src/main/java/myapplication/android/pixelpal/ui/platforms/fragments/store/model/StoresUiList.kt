package myapplication.android.pixelpal.ui.platforms.fragments.store.model

class StoresUiList(
    val stores: List<StoreUi>
)

class StoreUi(
    val id: Int,
    val name: String,
    val image: String,
    val domain: String?,
    val projects: Int?
)