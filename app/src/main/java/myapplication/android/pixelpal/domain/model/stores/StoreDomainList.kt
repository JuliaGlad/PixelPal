package myapplication.android.pixelpal.domain.model.stores

class StoreDomainList(
   val stores: List<StoreDomain>
)
class StoreDomain(
    val id: Int,
    val name: String,
    val image: String,
    val domain: String?,
    val projects: Int?
)