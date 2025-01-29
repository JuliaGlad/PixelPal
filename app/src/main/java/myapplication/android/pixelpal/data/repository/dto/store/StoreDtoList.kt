package myapplication.android.pixelpal.data.repository.dto.store

class StoreDtoList(
    val stores: List<StoreDto>
)
class StoreDto(
    val id: Int,
    val name: String,
    val image: String,
    val domain: String?,
    val projects: Int?
)